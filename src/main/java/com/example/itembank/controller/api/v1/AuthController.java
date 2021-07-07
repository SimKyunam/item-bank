package com.example.itembank.controller.api.v1;

import com.example.itembank.model.network.Header;
import com.example.itembank.model.network.request.TokenRequest;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.TokenResponse;
import com.example.itembank.model.network.response.UserResponse;
import com.example.itembank.service.impl.v1.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. 인증"})
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "회원가입", notes = "사용자 등록을 합니다.")
    @PostMapping("/signup")
    public Header<UserResponse.Base> signup(@RequestBody UserRequest.Base request) {
        return authService.signup(request);
    }

    @ApiOperation(value = "로그인", notes = "로그인하여, 토큰을 생성합니다.")
    @PostMapping("/login")
    public Header<TokenResponse> login(@RequestBody UserRequest.Auth request) {
        return authService.login(request);
    }

    @ApiOperation(value = "토큰 재생성", notes = "토큰 재발행 시간을 통해 다시 토큰을 발급합니다.")
    @PostMapping("/reissue")
    public Header<TokenResponse> reissue(@RequestBody TokenRequest request) {
        return authService.reissue(request);
    }
}
