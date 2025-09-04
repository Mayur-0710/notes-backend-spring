package com.example.notes.util;

import java.security.SecureRandom;
import java.util.Base64;

public class ShareToken {
    private static final SecureRandom RNG = new SecureRandom();
    public static String generate(){
        byte[] bytes = new byte[16];
        RNG.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}

