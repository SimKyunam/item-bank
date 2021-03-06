package com.example.itembank.service.impl.v1;

import com.example.itembank.base.util.JwtUtil;
import com.example.itembank.model.entity.RefreshToken;
import com.example.itembank.model.entity.User;
import com.example.itembank.model.enumclass.Authority;
import com.example.itembank.model.network.Header;
import com.example.itembank.model.network.request.TokenRequest;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.TokenResponse;
import com.example.itembank.model.network.response.UserResponse;
import com.example.itembank.repository.RefreshTokenRepository;
import com.example.itembank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Transactional
    public Header<UserResponse.Base> signup(UserRequest.Base request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        // 1. requestDto -> User
        User user = new User().dtoToEntityAndPwdEncoder(request, passwordEncoder);

        // 2. User 생성
        User newUser = userRepository.save(user);

        // 3. 생성된 데이터 -> UserApiResponse return
        return Header.OK(User.response(newUser));
    }

    @Transactional
    public Header<TokenResponse> login(UserRequest.Auth userRequest) {

        User user = User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .authority(Authority.ROLE_USER)
                .build();

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // TODO: 토큰 발행 에러
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponse tokenDto = jwtUtil.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return Header.OK(tokenDto);
    }

    @Transactional
    public Header<TokenResponse> reissue(TokenRequest tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!jwtUtil.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtUtil.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenResponse tokenDto = jwtUtil.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return Header.OK(tokenDto);
    }
}
