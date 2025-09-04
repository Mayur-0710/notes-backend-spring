package com.example.notes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {

    public static class RegisterReq {
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }

    public static class LoginReq {
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }

    public static class TokenRes {
        public String access_token;
        public String token_type = "bearer";
        public TokenRes(String t){ this.access_token = t; }
    }
}
