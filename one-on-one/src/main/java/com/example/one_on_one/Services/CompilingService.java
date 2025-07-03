package com.example.one_on_one.Services;
import java.util.*;

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
    public Map<String,Object> Run(Map<String,Object> code){
        Long id = Long.parseLong((String) code.get("id"));
        Questionsmodels question = questionRepo.findById(id).get();
        Gson gs = new Gson();
        JsonObject testcase = gs.fromJson(question.getTestcases(), JsonObject.class);
        String codes= (String) code.get("code");
        String escapedCode = convert(codes);
        String language = (String) code.get("lang");
        String ext = (String) code.get("ext");
        Map<String,Object> resend = new HashMap<>();
        for(int i=1;i<=2;i++){
            JsonObject test = gs.fromJson(testcase.get("test"+i),JsonObject.class);
            String tests =  convert(test.get("input").getAsString());
            String expectedop = convert(test.get("output").getAsString());
            String jsonBody = "{\n" +
                    "  \"language\": \"" + language + "\",\n"+
                    "  \"version\": \"*\",\n" +
                    "  \"files\": [\n" +
                    "    {\n" +
                    "      \"name\": \"main."+ext+"\",\n" +
                    "      \"content\": \"" +escapedCode + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "\"stdin\":\""+tests+"\""+
                    "}";
            HttpResponse<Map> res = Unirest.post("https://emkc.org/api/v2/piston/execute")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asObject(Map.class);
            Map<String,Object> response=res.getBody();
            Map<String,Object> ans = (Map<String, Object>) response.get("run");
            if(expectedop.trim().equals(((String) ans.get("stdout")).trim())){
                resend.put("testcase"+i+"status","success");
                resend.put("testcase"+i+"op",ans.get("stdout"));
                System.out.println("success");
            }else if(((String) ans.get("stderr"))!=null){
                resend.put("testcase"+i+"status","failed");
                resend.put("testcase"+i+"error",ans.get("stderr"));
            }else{
                resend.put("testcase"+i+"status","failed");
                resend.put("testcase"+i+"op",ans.get("stdout"));
            }

        }
        System.out.println(resend);
        return resend;
    }
    public Map<String,Object> Submit(Map<String,Object> code){
        Long id = Long.parseLong((String) code.get("id"));
        Questionsmodels question = questionRepo.findById(id).get();
        Gson gs = new Gson();
        JsonObject testcase = gs.fromJson(question.getTestcases(), JsonObject.class);
        String codes= (String) code.get("code");
        String escapedCode = convert(codes);
        String language = (String) code.get("lang");
        String ext = (String) code.get("ext");
        Map<String,Object> resend = new HashMap<>();
        int testcases=0;
        for(int i=1;i<=2;i++){

            JsonObject test = gs.fromJson(testcase.get("test"+i),JsonObject.class);
            String tests =  convert(test.get("input").getAsString());
            String expectedop = convert(test.get("output").getAsString());
            String jsonBody = "{\n" +
                    "  \"language\": \"" + language + "\",\n"+
                    "  \"version\": \"*\",\n" +
                    "  \"files\": [\n" +
                    "    {\n" +
                    "      \"name\": \"main."+ext+"\",\n" +
                    "      \"content\": \"" +escapedCode + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "\"stdin\":\""+tests+"\""+
                    "}";
            HttpResponse<Map> res = Unirest.post("https://emkc.org/api/v2/piston/execute")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asObject(Map.class);
            Map<String,Object> response=res.getBody();
            Map<String,Object> ans = (Map<String, Object>) response.get("run");
            if(expectedop.trim().equals(((String) ans.get("stdout")).trim())){
                resend.put("testcase"+i+"status","success");
                resend.put("testcase"+i+"op",ans.get("stdout"));
                System.out.println("success");
                testcases++;
            }else if(((String) ans.get("stderr"))!=null){
                resend.put("testcase"+i+"status","failed");
                resend.put("testcase"+i+"error",ans.get("stderr"));
            }else{
                resend.put("testcase"+i+"status","failed");
                resend.put("testcase"+i+"op",ans.get("stdout"));
            }
        }
        submissionmodel.setRoomCode((String) code.get("roomcode"));
        submissionmodel.setUser(userRepo.findByUsername(((String)code.get("username"))).get());
        submissionmodel.setTestcasepassed(testcases);
        submissionmodel.setTotaltestcase(2);
        if(testcases==5){
            submissionmodel.setScore((testcases*2)+5);
        }else {
            submissionmodel.setScore(testcases * 2);
        }
        submissionRepo.save(submissionmodel);
        return resend;
    }

}
