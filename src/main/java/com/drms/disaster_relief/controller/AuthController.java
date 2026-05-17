package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.entity.User;
import com.drms.disaster_relief.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, Object> requestData) {
        String result = authService.userSignup(requestData);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        System.out.println("Login attempt for: " + loginData.get("email"));
        try {
            String token = authService.login(loginData);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
