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
        String roomcode = (String) user.get("roomCode");
        String username = (String) user.get("username");
        UserModels users = userRepo.findByUsername(username).orElseThrow();
        Long userId = users.getId();

        // Only insert if not already present for this user and room
        if(!matchwinningrepo.existsByRoomAndUser(roomcode, users)) {
            matchwinning matchwinning = new matchwinning();
            matchwinning.setRoom(roomcode);
            users.setTotalmatch(users.getTotalmatch()+1);
            matchwinning.setUser(users);
            Integer totalscore = submissionRepo.getTotalScoreByUserAndRoom(roomcode, userId); // Pass user ID!
            matchwinning.setTotalScore(totalscore == null ? 0 : totalscore);
            matchwinningrepo.save(matchwinning);
        }

        List<matchwinning> matches = matchwinningrepo.findByRoom(roomcode);
        if(matches.size() >= 2){
            matchwinning m1 = matches.get(0);
            matchwinning m2 = matches.get(1);

            if(m1.getTotalScore() > m2.getTotalScore()){
                m1.setWinner(true);
                m1.getUser().setWins(m1.getUser().getWins()+1);
                m2.setWinner(false);

                m2.getUser().setLosses(m2.getUser().getLosses()+1);
            } else if(m1.getTotalScore() < m2.getTotalScore()){
                m2.setWinner(true);
                m2.getUser().setWins(m2.getUser().getWins()+1);
                m1.setWinner(false);

                m1.getUser().setLosses(m1.getUser().getLosses()+1);
            } else {
                m1.setWinner(true);
                m2.getUser().setWins(m2.getUser().getWins()+1);
                m1.getUser().setWins(m1.getUser().getWins()+1);
                m2.setWinner(true);
            }
            matchwinningrepo.save(m1);
            matchwinningrepo.save(m2);

            RoomModels roomModels = roomrepo.findByRoomCode(roomcode).orElseThrow();
            roomModels.setStatus(RoomModels.status.COMPLETED);
            roomrepo.save(roomModels);
        }

        return "completed";
    }

    public List<matchwinning> getans(String roomCode) {
        return matchwinningrepo.findByRoom(roomCode);
    }
}
