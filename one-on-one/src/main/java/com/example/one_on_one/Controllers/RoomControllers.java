package com.example.one_on_one.Controllers;

import com.example.one_on_one.Models.RoomModels;
import com.example.one_on_one.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class RoomControllers {
    @Autowired
    RoomService roomService;
    @PostMapping("/api/room/create")
    public String createroom(@RequestBody Map<String,Object> rooms){
        return roomService.create(rooms);
    }
    @MessageMapping("/room/info")
    @SendTo("/topic/room-info")
    public RoomModels getRoom(Map<String, String> payload) {
        String roomCode = payload.get("roomCode");
        return roomService.getRoom(roomCode);
    }
    // WebSocket controller to notify start
    @MessageMapping("/start-test/{roomCode}")
    @SendTo("/topic/start/{roomCode}")
    public String notifyStart(@DestinationVariable String roomCode) {
        return "start";
    }

    @PostMapping("/api/room/join")
    public String joinRoom(@RequestBody Map<String,Object> guest){
        return roomService.joinroom(guest);
    }
    @PostMapping("/api/room/start/{roomCode}")
    public String start(@PathVariable String roomCode){
        return roomService.start(roomCode);
    }
    @PostMapping("/api/room/end/{roomCode}")
    public String end(@PathVariable String roomCode){
        return roomService.end(roomCode);
    }
}
