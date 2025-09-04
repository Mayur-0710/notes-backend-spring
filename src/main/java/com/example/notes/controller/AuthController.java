package com.example.notes.controller;

import com.example.notes.dto.AuthDtos;
import com.example.notes.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService auth;
    public AuthController(AuthService auth){ this.auth = auth; }

    @PostMapping("/register")
    public ResponseEntity<AuthDtos.TokenRes> register(@Valid @RequestBody AuthDtos.RegisterReq req){
        String token = auth.register(req);
        return ResponseEntity.ok(new AuthDtos.TokenRes(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.TokenRes> login(@Valid @RequestBody AuthDtos.LoginReq req){
        String token = auth.login(req);
        return ResponseEntity.ok(new AuthDtos.TokenRes(token));
    }
}
