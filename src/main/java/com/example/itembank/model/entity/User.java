package com.example.itembank.model.entity;

import com.example.itembank.model.entity.base.BaseEntity;
import com.example.itembank.model.enumclass.Authority;
import com.example.itembank.model.enumclass.UserStatus;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collections;


@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String account;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Size(min = 0, max = 20)
    private String name;

    @Email
    private String email;

    private String phoneNumber;

    private String role;

    private LocalDateTime lastLoginAt;

    private LocalDateTime passwordUpdatedAt;

    private int loginFailCount;

    //활성화 시간?
    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    public static UserResponse.Base response(User user){
        //user -> userApiResponse
        UserResponse.Base userApiResponse = UserResponse.Base.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data return
        return userApiResponse;
    }

    public User dtoToEntity(UserRequest.Base request) {
        return User.builder()
                .account(request.getAccount())
                .password(request.getPassword())
                .name(request.getName())
                .status(request.getStatus())
                .authority(request.getAuthority())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
    }

    public User dtoToEntityAndPwdEncoder(UserRequest.Base request, PasswordEncoder passwordEncoder) {
        User user = this.dtoToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return user;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.toString());

        return new UsernamePasswordAuthenticationToken(email, password, Collections.singleton(grantedAuthority));
    }
}
