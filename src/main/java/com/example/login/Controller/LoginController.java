package com.example.login.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.Model.User;
import com.example.login.Model.DTOs.LoginResponseDTO;
import com.example.login.Model.DTOs.UserCreateDTO;
import com.example.login.Repository.UserRepository;
import com.example.login.Security.JWTUtil;

import java.util.Optional;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCreateDTO loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
    
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    
        User user = userOptional.get();
    
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
    
        var token = tokenService.generateToken(user.getUsername(), user.getEmail());
        LoginResponseDTO responseDTO = new LoginResponseDTO(token);
    
        return ResponseEntity.ok(responseDTO);
    }
}
