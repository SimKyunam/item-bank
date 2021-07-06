package com.example.itembank.base.util.jwt.ifs;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationTokenProvider {

    String parseTokenString(HttpServletRequest request);

    AuthenticationToken issue(Long userNo);

    Long getTokenOwnerNo(String token);

    boolean validateToken(String token);
}
