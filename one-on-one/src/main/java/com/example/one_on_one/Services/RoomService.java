package com.example.one_on_one.Services;

import com.example.one_on_one.Models.Questionsmodels;
import com.example.one_on_one.Models.RoomModels;
import com.example.one_on_one.Models.UserModels;
import com.example.one_on_one.Models.roomQuestionsModel;
import com.example.one_on_one.Repositries.QuestionRepo;
import com.example.one_on_one.Repositries.Roomrepo;
import com.example.one_on_one.Repositries.userRepo;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kong.unirest.*;

import java.util.*;

@Service
public class RoomService {
    @Autowired
    Roomrepo roomrepo;
    @Autowired RoomQuestionService roomQuestionService;
    @Autowired
    userRepo userrepo;
    @Autowired
    QuestionRepo questionRepo;
    public String generate(int len) {
        StringBuilder code = new StringBuilder();
        String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random rand = new Random();
        for (int i = 0; i < len; i++) {
            int val = rand.nextInt(character.length());
            code.append(character.charAt(val));
        }
        return code.toString();
    }

    public String create(Map<String, Object> rooms) {
        RoomModels room = new RoomModels();

        // Generate unique room code
        String RoomCode;
        do {
            RoomCode = generate(6);
        } while (roomrepo.existsByRoomCode(RoomCode));
        room.setRoom_code(RoomCode);

        // Difficulty
        String diff = String.valueOf(rooms.getOrDefault("difficulty", "EASY")).toUpperCase();
        Questionsmodels.Difficulty difficulty = Arrays.stream(Questionsmodels.Difficulty.values())
                .filter(d -> d.name().equalsIgnoreCase(diff))
                .findFirst()
                .orElse(Questionsmodels.Difficulty.EASY);
        room.setDifficulty(difficulty);

        // Status (optional - default to WAITING)
        String stat = String.valueOf(rooms.getOrDefault("status", "WAITING")).toUpperCase();
        room.setStatus(RoomModels.status.valueOf(stat));

        // Timer
        try {
            room.setTimer(Integer.parseInt(rooms.get("timer").toString()));
        } catch (Exception e) {
            return "Invalid or missing timer value";
        }

        // Host user (username is required)
        Object usernameObj = rooms.get("username");
        if (usernameObj == null) return "Missing username";
        String username = usernameObj.toString();
        room.setHost_user_id(userrepo.findByUsername(username).orElse(null));
        if (room.getHost_user_id() == null) return "Invalid host username";

        // Number of questions
        try {
            int numberOfQuestions = Integer.parseInt(rooms.getOrDefault("num_questions", "3").toString());
            room.setNum_questions(numberOfQuestions);
        } catch (Exception e) {
            return "Invalid or missing num_questions";
        }

        // Save and generate questions
        roomrepo.save(room);
        roomQuestionService.setques(getRoom(RoomCode));

        return RoomCode;
    }


    public RoomModels getRoom(String roomCode) {

        System.out.println("Extracted roomCode: " + roomCode);

        return roomrepo.findByRoomCode(roomCode).orElse(null);
    }



    public String joinroom(Map<String, Object> guest) {
        System.out.println("Received join request: " + guest);

        // Validate input
        if (guest == null || !guest.containsKey("roomcode") || !guest.containsKey("username")) {
            System.out.println("Missing 'roomcode' or 'username' in request: " + guest);
            return "failed: missing fields";
        }

        String roomCode = String.valueOf(guest.get("roomcode")).trim();
        String username = String.valueOf(guest.get("username")).trim();

        if (roomCode.isEmpty() || username.isEmpty()) {
            System.out.println("Empty 'roomcode' or 'username' values");
            return "failed: empty values";
        }

        // Optional: Normalize case (optional, depending on DB rules)
        // roomCode = roomCode.toUpperCase();
        // username = username.toLowerCase();

        Optional<RoomModels> optionalRoom = roomrepo.findByRoomCode(roomCode);
        Optional<UserModels> optionalUser = userrepo.findByUsername(username);

        if (optionalRoom.isEmpty()) {
            System.out.println("Room not found for code: " + roomCode);
            return "failed: room not found";
        }

        if (optionalUser.isEmpty()) {
            System.out.println("User not found: " + username);
            return "failed: user not found";
        }

        RoomModels existroom = optionalRoom.get();
        UserModels joiningUser = optionalUser.get();

        // Check if user is already the host
        if (existroom.getHost_user_id().getId().equals(joiningUser.getId())) {
            System.out.println("User is already the host of the room");
            return "failed: user already host";
        }

        // Check if guest already joined
        if (existroom.getGuest_user_id() != null) {
            System.out.println("Room already has a guest");
            return "failed: guest already joined";
        }

        // Join as guest
        existroom.setGuest_user_id(joiningUser);
        existroom.setStatus(RoomModels.status.READY);
        roomrepo.save(existroom);

        System.out.println("User " + username + " successfully joined room " + roomCode);
        return "success";
    }

    public RoomModels start(String roomCode) {
        RoomModels room = roomrepo.findByRoomCode(roomCode).get();
        room.setStatus(RoomModels.status.ONGOING);
        roomrepo.save(room);
        return room;
    }
    public String end(String roomCode) {
        RoomModels room = roomrepo.findByRoomCode(roomCode).get();
        room.setStatus(RoomModels.status.COMPLETED);
        roomrepo.save(room);
        return "Ended";
    }

    public int getTimer(String roomCode) {
        RoomModels room = roomrepo.findByRoomCode(roomCode).get();
        return room.getTimer();
    }
}
