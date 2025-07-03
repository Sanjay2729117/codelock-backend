package com.example.one_on_one.Services;

import com.example.one_on_one.Models.UserModels;
import com.example.one_on_one.Models.matchwinning;
import com.example.one_on_one.Repositries.SubmissionRepo;
import com.example.one_on_one.Repositries.matchwinningrepo;
import com.example.one_on_one.Models.RoomModels;
import com.example.one_on_one.Repositries.Roomrepo;
import com.example.one_on_one.Repositries.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MatchwinningService {
    @Autowired
    Roomrepo roomrepo;
    @Autowired
    userRepo userRepo;
    @Autowired
    SubmissionRepo submissionRepo;
    @Autowired
    matchwinningrepo matchwinningrepo;
    public String winner(Map<String,Object> user){
        String roomcode =(String) user.get("roomCode");
        UserModels users = userRepo.findByUsername((String) user.get("username")).get();
        if(matchwinningrepo.existsByRoom(roomcode)) {
            matchwinning matchwinning = new matchwinning();
            matchwinning.setRoom(roomcode);
            matchwinning.setUser(users);
            matchwinning.setTotalScore(submissionRepo.getTotalScoreByUserAndRoom(roomcode,(String) user.get("username")));
            matchwinningrepo.save(matchwinning);
        }else{
            matchwinning matchwinning = new matchwinning();
            matchwinning.setRoom(roomcode);
            matchwinning.setUser(users);
            matchwinning.setTotalScore(submissionRepo.getTotalScoreByUserAndRoom(roomcode,(String) user.get("username")));
            matchwinningrepo.save(matchwinning);
        }
        if(matchwinningrepo.findByRoom(roomcode).size()>=2){
            List<matchwinning> matches = matchwinningrepo.findByRoom(roomcode);
            if(matches.get(0).getTotalScore()>matches.get(1).getTotalScore()){
                matches.get(0).setWinner(true);
                matches.get(1).setWinner(false);
            }else if(matches.get(0).getTotalScore()<matches.get(1).getTotalScore()){
                matches.get(1).setWinner(true);
                matches.get(0).setWinner(false);
            }else{
                matches.get(1).setWinner(true);
                matches.get(0).setWinner(true);
            }
            matchwinningrepo.save(matches.get(0));
            matchwinningrepo.save(matches.get(1));
            RoomModels roomModels = roomrepo.findByRoomCode(roomcode).get();
            roomModels.setStatus(RoomModels.status.COMPLETED);
        }

        return "completed";
    }

    public List<matchwinning> getans(String roomCode) {
        return matchwinningrepo.findByRoom(roomCode);
    }
}
