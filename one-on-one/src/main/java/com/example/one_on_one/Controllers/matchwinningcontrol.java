package com.example.one_on_one.Controllers;

import com.example.one_on_one.Models.matchwinning;
import com.example.one_on_one.Services.MatchwinningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class matchwinningcontrol {
    @Autowired
    MatchwinningService matchwinningService;
    @MessageMapping("/match/winning")
    @SendTo("/topic/match-info")
    public List<matchwinning> check(String RoomCode){
        return matchwinningService.getans(RoomCode);
    }
    @PostMapping("/api/submittest")
    public String submittest(@RequestBody Map<String,Object> user){
        return matchwinningService.winner(user);
    }
}
