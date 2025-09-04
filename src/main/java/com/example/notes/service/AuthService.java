package com.example.notes.service;

import com.example.notes.dto.AuthDtos;
import com.example.notes.model.User;
import com.example.notes.repo.UserRepository;
import com.example.notes.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder enc;
    private final JwtUtil jwt;

    public AuthService(UserRepository users, PasswordEncoder enc, JwtUtil jwt){
        this.users = users; this.enc = enc; this.jwt = jwt;
    }

    public String register(AuthDtos.RegisterReq req){
        users.findByEmail(req.email).ifPresent(u -> { throw new RuntimeException("Email already registered"); });
        User u = new User();
        u.setEmail(req.email);
        u.setPasswordHash(enc.encode(req.password));
        users.save(u);
        return jwt.generateToken(u.getId().toString());
    }

    public String login(AuthDtos.LoginReq req){
        User u = users.findByEmail(req.email).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!enc.matches(req.password, u.getPasswordHash())) throw new RuntimeException("Invalid credentials");
        return jwt.generateToken(u.getId().toString());
    }
}
