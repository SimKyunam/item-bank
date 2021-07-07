package com.example.itembank.base.util;

import com.example.itembank.model.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private static final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){
        User user = User.builder()
                .id(1L)
                .name("John")
                .password("1111")
                .account("John")
                .build();

        String token = jwtUtil.createToken(user);
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("token value : " + token);
        System.out.println("------------------------------------------------------------------------------");

        assertTrue(token.contains("."));
    }

    @Test
    public void getClaims(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6eyJjcmVhdGVkQXQiOm51bGwsInVwZGF0ZWRBdCI6bnVsbCwiY3JlYXRlZEJ5IjpudWxsLCJ1cGRhdGVkQnkiOm51bGwsImlkIjoxLCJhY2NvdW50IjoiSm9obiIsInBhc3N3b3JkIjoiMTExMSIsInN0YXR1cyI6bnVsbCwibmFtZSI6IkpvaG4iLCJlbWFpbCI6bnVsbCwicGhvbmVOdW1iZXIiOm51bGwsInJvbGUiOm51bGwsImxhc3RMb2dpbkF0IjpudWxsLCJwYXNzd29yZFVwZGF0ZWRBdCI6bnVsbCwibG9naW5GYWlsQ291bnQiOjAsInJlZ2lzdGVyZWRBdCI6bnVsbCwidW5yZWdpc3RlcmVkQXQiOm51bGx9LCJpYXQiOjE2MjU2MjU5OTcsImV4cCI6MTYyNTY2MTk5N30.fyqW8MzU8P7i8VtgkQPDffmqZHQvlm-YhO6jp5SluAI";
        Claims claims = jwtUtil.getClaims(token);

        assertEquals(Long.parseLong(claims.get("user", Map.class).get("id").toString()), 1004L);
        assertEquals(claims.get("user", Map.class).get("name"), "John");
    }
}