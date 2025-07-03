package com.example.one_on_one.Controllers;

import com.example.one_on_one.Services.CompilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class CompillingControllers {
    @Autowired
    CompilingService compilingService;
    @PostMapping("/api/run")
    public Map<String,Object> run(@RequestBody Map<String,Object> code){
        return compilingService.Run(code);
    }
    @PostMapping("/api/submit")
    public Map<String,Object> submit(@RequestBody Map<String,Object> code){
        return compilingService.Submit(code);
    }

}
