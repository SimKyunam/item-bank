package com.example.itembank.base.util;

import com.example.itembank.model.entity.v1.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;

class JwtUtilTest {
    private static final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){
        //eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImNyZWF0ZWRBdCI6bnVsbCwidXBkYXRlZEF0IjpudWxsLCJjcmVhdGVkQnkiOm51bGwsInVwZGF0ZWRCeSI6bnVsbCwiaWQiOjEwMDQsImFjY291bnQiOm51bGwsInBhc3N3b3JkIjpudWxsLCJzdGF0dXMiOm51bGwsIm5hbWUiOiJKb2huIiwiZW1haWwiOm51bGwsInBob25lTnVtYmVyIjpudWxsLCJyb2xlIjpudWxsLCJsYXN0TG9naW5BdCI6bnVsbCwicGFzc3dvcmRVcGRhdGVkQXQiOm51bGwsImxvZ2luRmFpbENvdW50IjowLCJyZWdpc3RlcmVkQXQiOm51bGwsInVucmVnaXN0ZXJlZEF0IjpudWxsfSwiaWF0IjoxNjI1NTU5MzA4LCJleHAiOjE2MjU1NjExMDh9.n03O_uGgYgsH2ksT76klj4tqPnqOjmE9rTioYYzY3RA
        User user = User.builder()
                .id(1004L)
                .name("John")
                .build();

        String token = jwtUtil.createToken(user);

        assertEquals(token, "a");
        assertTrue(token.contains("."));
    }

    @Test
    public void getClaims(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDA0IiwidXNlciI6eyJjcmVhdGVkQXQiOm51bGwsInVwZGF0ZWRBdCI6bnVsbCwiY3JlYXRlZEJ5IjpudWxsLCJ1cGRhdGVkQnkiOm51bGwsImlkIjoxMDA0LCJhY2NvdW50IjpudWxsLCJwYXNzd29yZCI6bnVsbCwic3RhdHVzIjpudWxsLCJuYW1lIjoiSm9obiIsImVtYWlsIjpudWxsLCJwaG9uZU51bWJlciI6bnVsbCwicm9sZSI6bnVsbCwibGFzdExvZ2luQXQiOm51bGwsInBhc3N3b3JkVXBkYXRlZEF0IjpudWxsLCJsb2dpbkZhaWxDb3VudCI6MCwicmVnaXN0ZXJlZEF0IjpudWxsLCJ1bnJlZ2lzdGVyZWRBdCI6bnVsbH0sImlhdCI6MTYyNTU2MTY0MiwiZXhwIjoxNjI1NTk3NjQyfQ.8wckulEchKEsFwckP4i1iCJdR9igK7ENufObXr9WkBA";
        Claims claims = jwtUtil.getClaims(token);

        assertEquals(Long.parseLong(claims.get("user", Map.class).get("id").toString()), 1004L);
        assertEquals(claims.get("user", Map.class).get("name"), "John");
    }
}