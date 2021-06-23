package com.example.itembank.service.impl.v1;

import com.example.itembank.model.entity.User;
import com.example.itembank.model.enumclass.UserStatus;
import com.example.itembank.model.network.Header;
import com.example.itembank.model.network.Pagination;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.UserResponse;
import com.example.itembank.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<UserRequest.Base, UserResponse.Base, User> {

    @Override
    public Header<UserResponse.Base> create(Header<UserRequest.Base> request) {

        // 1. request data
        UserRequest.Base userRequest = request.getData();

        // 2. User 생성
        User user = User.builder()
                .account(userRequest.getAccount())
                .password(userRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userRequest.getPhoneNumber())
                .email(userRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = baseRepository.save(user);

        // 3. 생성된 데이터 -> UserApiResponse return
        return Header.OK(this.response(newUser));
    }

    @Override
    public Header<UserResponse.Base> read(Long id) {
        //id -> repository getOne, getById
        Optional<User> optional = baseRepository.findById(id);

        //user -> userApiResponse return
        /* 해당 메소드가 :: 표기법으로 표시하면 아래로 변경
        return optional
                .map(user -> {
                    return response(user);
                })
                .map(response->{
                    return Header.OK(response);
                })
                .orElseGet(() ->{
                    return Header.ERROR("데이터 없음");
                });
         */

        return optional
                .map(this::response)
                .map(Header::OK).orElseGet(() ->{
                    return Header.ERROR("데이터 없음");
                });
    }

    @Override
    public Header<UserResponse.Base> update(Header<UserRequest.Base> request) {
        // 1. data
        UserRequest.Base userRequest = request.getData();

        // 2. id -> user 데이터를 찾고
        Optional<User> optional = baseRepository.findById(userRequest.getId());

        // 3. 데이터 수정
        return optional.map(user -> {
            user.setAccount(userRequest.getAccount())
                    .setPassword(userRequest.getPassword())
                    .setStatus(userRequest.getStatus())
                    .setPhoneNumber(userRequest.getPhoneNumber())
                    .setEmail(userRequest.getEmail())
                    .setRegisteredAt(userRequest.getRegisteredAt())
                    .setUnregisteredAt(userRequest.getUnregisteredAt());
            return user;
        })
        .map(user -> baseRepository.save(user))
        .map(this::response)
        .map(Header::OK)
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<User> optional = baseRepository.findById(id);

        // 2. repository -> delete
        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
        })
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<List<UserResponse.Base>> search(Pageable pageable) {
        Page<User> users = baseRepository.findAll(pageable);
        List<UserResponse.Base> userResponseList = users.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())
                .build();

        return Header.OK(userResponseList, pagination);
    }


    private UserResponse.Base response(User user){
        //user -> userApiResponse
        UserResponse.Base userApiResponse = UserResponse.Base.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data return
        return userApiResponse;
    }
}
