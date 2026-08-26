package com.drms.disaster_relief.auth.controller;

import com.drms.disaster_relief.auth.dto.request.LoginDTO;
import com.drms.disaster_relief.auth.dto.request.UserDTO;
import com.drms.disaster_relief.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        String result = authService.userSignUp(userDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/userSignup")
    public ResponseEntity<?> userSignUp(@RequestBody UserDTO userDTO) {
        return signUp(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("Login attempt for: " + loginDTO.getEmail());
        try {
            String token = authService.login(loginDTO);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
