package com.example.one_on_one.Services;
import java.util.*;

import com.example.one_on_one.Models.UserModels;
import com.example.one_on_one.Repositries.SubmissionRepo;
import com.example.one_on_one.Repositries.userRepo;
import com.example.one_on_one.Models.Questionsmodels;
import com.example.one_on_one.Models.Submissionmodel;
import com.example.one_on_one.Repositries.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kong.unirest.*;
@Service
public class CompilingService {
    @Autowired
    QuestionRepo questionRepo;

    Submissionmodel submissionmodel = new Submissionmodel();
    @Autowired
    SubmissionRepo submissionRepo;
    @Autowired
    userRepo userRepo;
    public String convert(String tests){
        tests = tests.replace("\\","\\\\")
                .replace("\"","\\\"")
                .replace("\n","\\n")
                .replace("\r","\\r");
        return tests;
    }
    public Map<String,Object> Run(Map<String,Object> code) {
        Object idObj = code.get("id");
        Long id = null;
        if (idObj instanceof Number) {
            id = ((Number) idObj).longValue();
        } else if (idObj instanceof String) {
            id = Long.parseLong((String) idObj);
        } else {
            throw new IllegalArgumentException("Invalid id type: " + idObj);
        }
        Questionsmodels question = questionRepo.findById(id).get();
        Gson gs = new Gson();
        JsonObject testcase = gs.fromJson(question.getTestcases(), JsonObject.class);
        String codes = (String) code.get("code");
        String escapedCode = convert(codes);
        String language = (String) code.get("lang");
        String ext = (String) code.get("ext");
        Map<String, Object> resend = new HashMap<>();
        for (int i = 1; i <= 2; i++) {
            JsonObject test = gs.fromJson(testcase.get("test" + i), JsonObject.class);
            String tests = convert(test.get("input").getAsString());
            String expectedop = convert(test.get("output").getAsString());
            String jsonBody = "{\n" +
                    "  \"language\": \"" + language + "\",\n" +
                    "  \"version\": \"*\",\n" +
                    "  \"files\": [\n" +
                    "    {\n" +
                    "      \"name\": \"main." + ext + "\",\n" +
                    "      \"content\": \"" + escapedCode + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "\"stdin\":\"" + tests + "\"" +
                    "}";
            HttpResponse<Map> res = Unirest.post("https://emkc.org/api/v2/piston/execute")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asObject(Map.class);

            Map<String, Object> response = res.getBody();
            Map<String, Object> ans = (Map<String, Object>) response.get("run");
            String actualOp = ans.get("stdout") == null ? "" : ((String) ans.get("stdout")).trim();
            String errOp = ans.get("stderr") == null ? "" : (String) ans.get("stderr");

            if (expectedop.trim().equals(actualOp)) {
                resend.put("testcase" + i + "status", "success");
                resend.put("testcase" + i + "op", actualOp);
                System.out.println("success");
            } else if (!errOp.isEmpty()) {
                resend.put("testcase" + i + "status", "failed");
                resend.put("testcase" + i + "error", errOp);
                resend.put("testcase" + i + "op", actualOp); // Always include the output
            } else {
                resend.put("testcase" + i + "status", "failed");
                resend.put("testcase" + i + "op", actualOp);
            }
        }
            System.out.println(resend);
            return resend;
        }
    public Map<String, Object> Submit(Map<String, Object> code) {
        // 1. Extract and parse input data
        Object idObj = code.get("id");
        Long questionId = (idObj instanceof Number) ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString());

        Questionsmodels question = questionRepo.findById(questionId).orElseThrow();
        Gson gs = new Gson();
        JsonObject testcase = gs.fromJson(question.getTestcases(), JsonObject.class);

        String codes = (String) code.get("code");
        String escapedCode = convert(codes);
        String language = (String) code.get("lang");
        String ext = (String) code.get("ext");
        String RoomCode = (String) code.get("roomcode");    // Capital-R for consistency!
        String username = (String) code.get("username");

        // 2. Find the user entity from username
        UserModels user = userRepo.findByUsername(username).orElseThrow();

        Map<String, Object> resend = new HashMap<>();
        int testcasesPassed = 0;
        int totalTestcases = testcase.size();

        // 3. Judge & record all testcases
        for (int i = 1; i <= totalTestcases; i++) {
            JsonObject test = testcase.getAsJsonObject("test" + i);
            String tests = convert(test.get("input").getAsString());
            String expectedOutput = convert(test.get("output").getAsString());

            String jsonBody = "{" +
                    "\"language\": \"" + language + "\"," +
                    "\"version\": \"*\"," +
                    "\"files\": [{\"name\": \"main." + ext + "\", \"content\": \"" + escapedCode + "\"}]," +
                    "\"stdin\": \"" + tests + "\"}";

            HttpResponse<Map> res = Unirest.post("https://emkc.org/api/v2/piston/execute")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asObject(Map.class);

            Map<String, Object> response = res.getBody();
            Map<String, Object> runResult = (Map<String, Object>) response.get("run");
            String stdout = (String) runResult.get("stdout");
            String stderr = (String) runResult.get("stderr");
            String actualOp = stdout == null ? "" : stdout.trim();

            if (expectedOutput.trim().equals(actualOp)) {
                resend.put("testcase" + i + "status", "success");
                resend.put("testcase" + i + "op", actualOp);
                testcasesPassed++;
            } else {
                resend.put("testcase" + i + "status", "failed");
                resend.put("testcase" + i + "op", actualOp);
                if (stderr != null && !stderr.trim().isEmpty())
                    resend.put("testcase" + i + "error", stderr);
            }
        }

        // 4. Find or create + save submission
        Optional<Submissionmodel> existing = submissionRepo
                .findByRoomCodeAndUser_IdAndQuestionId(RoomCode, user.getId(), questionId);
        Submissionmodel submission;
        if (existing.isPresent()) {
            submission = existing.get();
        } else {
            submission = new Submissionmodel();
            submission.setRoomCode(RoomCode);
            submission.setUser(user);
            submission.setQuestionId(questionId);
        }

        submission.setTestcasePassed(testcasesPassed);
        submission.setTotalTestcase(totalTestcases);
        int score = testcasesPassed * 2;
        if (testcasesPassed == totalTestcases) score += 5;
        submission.setScore(score);

        submissionRepo.save(submission);
        return resend;
    }

}

