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
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDA0IiwidXNlciI6eyJjcmVhdGVkQXQiOm51bGwsInVwZGF0ZWRBdCI6bnVsbCwiY3JlYXRlZEJ5IjpudWxsLCJ1cGRhdGVkQnkiOm51bGwsImlkIjoxMDA0LCJhY2NvdW50IjpudWxsLCJwYXNzd29yZCI6bnVsbCwic3RhdHVzIjpudWxsLCJuYW1lIjoiSm9obiIsImVtYWlsIjpudWxsLCJwaG9uZU51bWJlciI6bnVsbCwicm9sZSI6bnVsbCwibGFzdExvZ2luQXQiOm51bGwsInBhc3N3b3JkVXBkYXRlZEF0IjpudWxsLCJsb2dpbkZhaWxDb3VudCI6MCwicmVnaXN0ZXJlZEF0IjpudWxsLCJ1bnJlZ2lzdGVyZWRBdCI6bnVsbH0sImlhdCI6MTYyNTU3NDgyOCwiZXhwIjoxNjI1NjEwODI4fQ.owIQL_fkDtCJ5Aj3RqUeXMmzeLXCrYNnpofKYH13ofI";
        Claims claims = jwtUtil.getClaims(token);

        assertEquals(Long.parseLong(claims.get("user", Map.class).get("id").toString()), 1004L);
        assertEquals(claims.get("user", Map.class).get("name"), "John");
    }
}