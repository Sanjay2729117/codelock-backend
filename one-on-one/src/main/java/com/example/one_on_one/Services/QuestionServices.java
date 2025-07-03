package com.example.one_on_one.Services;

import com.example.one_on_one.Models.Questionsmodels;
import com.example.one_on_one.Repositries.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionServices {
//    {
//        "title": "2 Sum",
//            "description": "Given an array of integers...",
//            "constraints": "1 <= nums.length <= 10^4",
//            "inputFormat": "nums = [2,7,11,15], target = 9",
//            "outputFormat": "[0,1]",
//            "testcases": [
//        {
//            "input": "[2,7,11,15], 9",
//                "output": "[0,1]"
//        },
//        {
//            "input": "[3,2,4], 6",
//                "output": "[1,2]"
//        }
//  ],
//        "tags": ["array", "hashmap"],
//        "difficulty": "EASY"
//    }
    @Autowired
    QuestionRepo questionRepo;
    public String addquestion(Map<String, Object> questiondetails) {
        Questionsmodels questionsmodels = new Questionsmodels();

        // Safely extract and check for nulls
        Object titleObj = questiondetails.get("title");
        if (titleObj == null) {
            return "Error: title is missing";
        }
        String title = titleObj.toString();

        Object descriptionObj = questiondetails.get("description");
        if (descriptionObj == null) {
            return "Error: description is missing";
        }
        String description = descriptionObj.toString();

        Object constraintsObj = questiondetails.get("constraints");
        if (constraintsObj == null) {
            return "Error: constraints is missing";
        }
        String constraints = constraintsObj.toString();

        Object inputFormatObj = questiondetails.get("inputFormat");
        if (inputFormatObj == null) {
            return "Error: inputFormat is missing";
        }
        String inputFormat = inputFormatObj.toString();

        Object outputFormatObj = questiondetails.get("outputFormat");
        if (outputFormatObj == null) {
            return "Error: outputFormat is missing";
        }
        String outputFormat = outputFormatObj.toString();

        Object testcasesObj = questiondetails.get("testcases");
        if (testcasesObj == null) {
            return "Error: testcases is missing";
        }
        String testcases = testcasesObj.toString();

        Object tagsObj = questiondetails.get("tags");
        if (tagsObj == null) {
            return "Error: tags is missing";
        }
        String tags = tagsObj.toString();

        Object difficultyObj = questiondetails.get("difficulty");
        if (difficultyObj == null) {
            return "Error: difficulty is missing";
        }
        Questionsmodels.Difficulty difficulty = Questionsmodels.Difficulty.valueOf(difficultyObj.toString());

        questionsmodels.setTitle(title);
        questionsmodels.setDescription(description);
        questionsmodels.setConstraints(constraints);
        questionsmodels.setInput_format(inputFormat);
        questionsmodels.setOutput_format(outputFormat);
        questionsmodels.setTestcases(testcases);
        questionsmodels.setTags(tags);
        questionsmodels.setDifficulty(difficulty);

        questionRepo.save(questionsmodels);
        return "success";
    }

    public Questionsmodels getQuestionById(Long id) {
        return questionRepo.findById(id).get();
    }

    public Questionsmodels getQuestionBytitle(String title) {
        return questionRepo.findBytitle(title).get();
    }

    public String updateQuestion(Long id,Map<String,Object> upquestion) {
        Questionsmodels question = questionRepo.findById(id).get();
        Object titleObj = upquestion.get("title");
        if (titleObj == null) {
            return "Error: title is missing";
        }
        String title = titleObj.toString();

        Object descriptionObj = upquestion.get("description");
        if (descriptionObj == null) {
            return "Error: description is missing";
        }
        String description = descriptionObj.toString();

        Object constraintsObj = upquestion.get("constraints");
        if (constraintsObj == null) {
            return "Error: constraints is missing";
        }
        String constraints = constraintsObj.toString();

        Object inputFormatObj = upquestion.get("inputFormat");
        if (inputFormatObj == null) {
            return "Error: inputFormat is missing";
        }
        String inputFormat = inputFormatObj.toString();

        Object outputFormatObj = upquestion.get("outputFormat");
        if (outputFormatObj == null) {
            return "Error: outputFormat is missing";
        }
        String outputFormat = outputFormatObj.toString();

        Object testcasesObj = upquestion.get("testcases");
        if (testcasesObj == null) {
            return "Error: testcases is missing";
        }
        String testcases = testcasesObj.toString();

        Object tagsObj = upquestion.get("tags");
        if (tagsObj == null) {
            return "Error: tags is missing";
        }
        String tags = tagsObj.toString();

        Object difficultyObj = upquestion.get("difficulty");
        if (difficultyObj == null) {
            return "Error: difficulty is missing";
        }
        Questionsmodels.Difficulty difficulty = Questionsmodels.Difficulty.valueOf(difficultyObj.toString());

        question.setTitle(title);
        question.setDescription(description);
        question.setConstraints(constraints);
        question.setInput_format(inputFormat);
        question.setOutput_format(outputFormat);
        question.setTestcases(testcases);
        question.setTags(tags);
        question.setDifficulty(difficulty);
        questionRepo.save(question);
        return "success";
    }
}
