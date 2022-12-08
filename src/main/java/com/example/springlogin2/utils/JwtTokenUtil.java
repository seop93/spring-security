package com.example.springlogin2.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    public static String generateToken(String userName, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("userName", userName); // Token에 담는 정보를 Claim이라고 함

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 만든 날짜
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs)) //끝나는 날짜
                .signWith(SignatureAlgorithm.HS256, key) //키를 가지고 사인
                .compact();
    }
}
