package com.example.itembank.base.util;

import com.example.itembank.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public class JwtUtil {

    private Key key;

    // 토큰 유효시간 30분
    private long tokenValidTime =  10 * 60 * 60 * 1000L;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(User user) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("user", user);

        return builder
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Jwt Token에서 User Id 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}