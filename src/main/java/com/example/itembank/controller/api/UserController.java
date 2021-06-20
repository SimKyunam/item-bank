package com.example.itembank.controller.api;

import com.example.itembank.controller.CrudController;
import com.example.itembank.model.entity.User;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/user")
public class UserController extends CrudController<UserRequest, UserResponse, User> {

    @GetMapping("test/{id}")
    public ResponseEntity<User> select(@PathVariable Long id){
        return new ResponseEntity<User>(HttpStatus.OK);
    }
}
