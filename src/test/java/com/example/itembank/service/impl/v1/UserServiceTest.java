package com.example.itembank.service.impl.v1;

import com.example.itembank.model.entity.User;
import com.example.itembank.model.enumclass.UserStatus;
import com.example.itembank.model.network.Header;
import com.example.itembank.model.network.response.UserResponse;
import com.example.itembank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    //기초데이터 설정
    List<User> users;
    User user;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        userService = new UserService(userRepository, passwordEncoder);

        //------------데이터 세팅------------
        users = Arrays.asList(
                User.builder().id(1L).email("test@naver.com").name("심규남").account("test1").password("1111").status(UserStatus.REGISTERED).build(),
                User.builder().id(2L).email("test1@gmail.com").name("홍길동").account("test2").password("1111").status(UserStatus.UNREGISTERED).build(),
                User.builder().id(3L).email("test2@daum.com").name("강감찬").account("test3").password("1111").status(UserStatus.REGISTERED).build(),
                User.builder().id(4L).email("test3@naver.com").name("박나라").account("test4").password("1111").status(UserStatus.UNREGISTERED).build(),
                User.builder().id(5L).email("test4@gmail.com").name("김진수").account("test5").password("1111").status(UserStatus.REGISTERED).build()
        );

        user = User.builder().id(10L).email("single@test.com").name("장그레").account("single").password("1111").status(UserStatus.REGISTERED).build();
        //------------데이터 세팅------------
    }

    @Test
    public void read(){
        given(userRepository.findById(10L)).willReturn(Optional.of(user));

        Header<UserResponse.Base> mockUserResponse = userService.read(10L);
        UserResponse.Base userResponseData = mockUserResponse.getData();

        assertEquals(user.getName(), userResponseData.getName());
    }

    @Test
    public void create(){

    }
}