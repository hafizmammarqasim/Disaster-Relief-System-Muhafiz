package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.LoginDTO;
import com.drms.disaster_relief.dto.NgoDTO;
import com.drms.disaster_relief.dto.UserDTO;
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

    @PostMapping("/userSignup")
    public ResponseEntity<?> userSignUp(@RequestBody UserDTO request) {
        String result = authService.userSignUp(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        System.out.println("Login attempt for: " + request.getEmail());
        try {
            String token = authService.login(request);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/ngoSignup")
    public ResponseEntity<?> ngoSignUp(@RequestBody NgoDTO request) {
        String result = authService.ngoRegistration(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
