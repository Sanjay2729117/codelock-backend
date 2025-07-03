package com.example.one_on_one.Services;

import com.example.one_on_one.Models.Questionsmodels;
import com.example.one_on_one.Models.RoomModels;
import com.example.one_on_one.Models.roomQuestionsModel;
import com.example.one_on_one.Repositries.QuestionRepo;
import com.example.one_on_one.Repositries.RoomQuestionsrepo;
import com.example.one_on_one.Repositries.Roomrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomQuestionService {
    @Autowired
    RoomQuestionsrepo roomQuestionsrepo;
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    Roomrepo roomrepo;

    public List<roomQuestionsModel> getQuestionsByRoomCode(String roomCode) {
        return roomQuestionsrepo.findByRoom(roomCode);
    }



    public void setques(RoomModels room) {
        List<Questionsmodels> questionsmodels = questionRepo.findQuestionsByDifficultyWithLimit(room.getDifficulty(),room.getNum_questions());
        int position=1;
         for (Questionsmodels ques:questionsmodels){
            roomQuestionsModel roomQuestionsModel = new roomQuestionsModel();
            roomQuestionsModel.setRoom(room.getRoom_code());
            roomQuestionsModel.setQuestions_id(ques);
            roomQuestionsModel.setPosition(position);
            position+=1;
            roomQuestionsrepo.save(roomQuestionsModel);
        }

    }
}
