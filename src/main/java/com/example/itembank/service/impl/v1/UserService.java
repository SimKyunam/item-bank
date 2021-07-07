package com.example.itembank.service.impl.v1;

import com.example.itembank.model.entity.User;
import com.example.itembank.model.network.Header;
import com.example.itembank.model.network.Pagination;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.UserResponse;
import com.example.itembank.repository.UserRepository;
import com.example.itembank.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService extends BaseService<UserRequest.Base, UserResponse.Base, User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Header<UserResponse.Base> create(UserRequest.Base request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        // 1. requestDto -> User
        User user = new User().dtoToEntityAndPwdEncoder(request, passwordEncoder);

        // 2. User 생성
        User newUser = baseRepository.save(user);

        // 3. 생성된 데이터 -> UserApiResponse return
        return Header.OK(this.response(newUser));
    }

    @Override
    public Header<UserResponse.Base> read(Long id) {
        //id -> repository getOne, getById
        Optional<User> optional = baseRepository.findById(id);

        return optional
                .map(this::response)
                .map(Header::OK).orElseGet(() ->{
                    return Header.ERROR("데이터 없음");
                });
    }

    @Override
    public Header<UserResponse.Base> update(UserRequest.Base request) {
        // 1. data
        UserRequest.Base userRequest = request;

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
}
