package com.example.itembank.model.network.response;

import com.example.itembank.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class UserResponse {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Base{
        private Long id ;

        private String account;

        private String password;

        private UserStatus status;

        private String name;

        private String email;

        private String phoneNumber;

        private LocalDateTime registeredAt;

        private LocalDateTime unregisteredAt;
    }


}
