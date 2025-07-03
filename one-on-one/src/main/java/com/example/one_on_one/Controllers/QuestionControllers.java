package com.example.one_on_one.Controllers;

import com.example.one_on_one.Models.Questionsmodels;
import com.example.one_on_one.Models.roomQuestionsModel;
import com.example.one_on_one.Services.QuestionServices;
import com.example.one_on_one.Services.RoomQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class QuestionControllers {
    @Autowired
    QuestionServices questionServices;
    @Autowired
    RoomQuestionService roomQuestionService;
    @PostMapping("/api/questions")
    public String addQuestion(@RequestBody Map<String,Object> questiondetails){
        return questionServices.addquestion(questiondetails);
    }
    @GetMapping("/api/questions/{id}")
    public Questionsmodels getById(@PathVariable Long id) {
        return questionServices.getQuestionById(id);
    }
//    @GetMapping("/api/questions/{title}")
//    public Questionsmodels getQuestion(@PathVariable String title) {
//        System.out.println(title);
//        return questionServices.getQuestionBytitle(title);
//    }
    @PutMapping("/api/questions/{id}")
    public String UpdateQuestion(@PathVariable Long id,@RequestBody Map<String,Object> question){
        return questionServices.updateQuestion(id,question);
    }
    @GetMapping("/api/questions/get/{roomCode}")
    public List<roomQuestionsModel> getQues(@PathVariable String roomCode){
        return roomQuestionService.getQuestionsByRoomCode(roomCode);
    }


}
