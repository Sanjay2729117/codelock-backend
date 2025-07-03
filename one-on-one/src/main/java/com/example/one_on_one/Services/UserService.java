package com.example.one_on_one.Services;

import com.example.one_on_one.Models.UserModels;
import com.example.one_on_one.Repositries.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private userRepo repositry;
    public String register(Map<String, Object> user) {
        String username = user.get("username").toString();
        String email = user.get("email").toString();
        String password = user.get("password").toString();
        Optional<UserModels> dbemail = repositry.findByemail(email);
        Optional<UserModels> dbusername = repositry.findByUsername(username);
        if(dbemail.isPresent()){
            return "Email already registered";
        }
        if(dbusername.isPresent()){
            return "username is already taken";
        }
        UserModels userCreate = new UserModels();
        userCreate.setUsername(username);
        userCreate.setEmail(email);
        userCreate.setPassword(password);
        userCreate.setLosses(0);
        userCreate.setWins(0);
        userCreate.setTotalmatch(0);

        repositry.save(userCreate);
        return "success";
    }

    public Optional<UserModels> login(Map<String, Object> user) {
        String email = user.get("email").toString();
        String password = user.get("password").toString();
        Optional<UserModels> dbUser = repositry.findByemail(email);

        if (dbUser.isPresent() && dbUser.get().getPassword().equals(password)) {
            return dbUser;
        }

        return Optional.empty();
    }


    public Optional<UserModels> getprofile(Long id) {
        return repositry.findById(id);
    }

    public Optional<UserModels> getprofileByusername(String username) {
        return repositry.findByUsername(username);
    }

    public Optional<UserModels> getprofilebyemail(String email) {
        return repositry.findByemail(email);
    }
}
