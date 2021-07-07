package com.example.itembank.model.entity;

import com.example.itembank.model.entity.base.BaseEntity;
import com.example.itembank.model.enumclass.Authority;
import com.example.itembank.model.enumclass.UserStatus;
import com.example.itembank.model.network.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


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

    public <T extends UserRequest.Base> User dtoToEntity(T request) {
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

    public <T extends UserRequest.Base> User dtoToEntityAndPwdEncoder(T request, PasswordEncoder passwordEncoder) {
        User user = this.dtoToEntity(request);
        user.setPassword(passwordEncoder.encode(password));

        return user;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
