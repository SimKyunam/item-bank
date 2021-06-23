package com.example.itembank.controller.api.v1;

import com.example.itembank.model.entity.User;
import com.example.itembank.model.enumclass.UserStatus;
import com.example.itembank.model.network.Header;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.UserResponse;
import com.example.itembank.service.impl.v1.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    //기초데이터 설정
    List<User> users;
    User user;

    @BeforeEach
    public void setUp(){
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
    public void search() throws Exception{
        List<UserResponse.Base> userResponseList = users.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Header mockUserResponseList = Header.OK(userResponseList);

        given(userService.search(any())).willReturn(mockUserResponseList);

        mvc.perform(get("/v1/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test@naver.com")));
    }

    @Test
    public void create() throws Exception {
        Header mockUserRequest = Header.builder().data(user).build();
        given(userService.create(any())).willReturn(mockUserRequest);

        mvc.perform(post("/v1/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"10\", \"email\":\"single@test.com\", \"name\":\"장그레\", \"account\":\"single\", \"password\":\"1111\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("장그레")));
    }


    public UserResponse.Base response(User user){
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

        return userApiResponse;
    }
}