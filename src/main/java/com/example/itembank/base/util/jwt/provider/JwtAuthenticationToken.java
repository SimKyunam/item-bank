package com.example.itembank.base.util.jwt.provider;

import com.example.itembank.base.util.jwt.ifs.AuthenticationToken;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthenticationToken implements AuthenticationToken {
    private String token;
}
