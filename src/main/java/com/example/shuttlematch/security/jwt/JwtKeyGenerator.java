package com.example.shuttlematch.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Tạo khóa bí mật an toàn
        String secret = java.util.Base64.getEncoder().encodeToString(key.getEncoded()); // Chuyển khóa sang dạng chuỗi Base64
        System.out.println("Generated secret key: " + secret);
    }
}
