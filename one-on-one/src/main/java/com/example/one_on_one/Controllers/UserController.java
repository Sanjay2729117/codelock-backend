package com.example.one_on_one.Controllers;

import com.example.one_on_one.Models.UserModels;
import com.example.one_on_one.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/api/auth/register")
    public String register(@RequestBody Map<String,Object> user){
        return userService.register(user);
    }
    @PostMapping("/api/auth/login")
    public Optional<UserModels> login(@RequestBody Map<String,Object> users){
        return userService.login(users);
    }
    @GetMapping("/api/users/profile/id/{id}")
    public Optional<UserModels> getprofileById(@PathVariable Long id) {
        return userService.getprofile(id);
    }
    @GetMapping("/api/users/profile/username/{username}")
    public Optional<UserModels> getprofileByUsername(@PathVariable String username) {
        return userService.getprofileByusername(username);
    }
    @GetMapping("/api/users/profile/{email}")
    public Optional<UserModels> getprofilebyemail(@PathVariable String email) {
        return userService.getprofilebyemail(email);
    }

}
