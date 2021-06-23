package com.example.itembank.controller.api.v1;

import com.example.itembank.controller.BaseController;
import com.example.itembank.model.entity.v1.User;
import com.example.itembank.model.network.Header;
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
//@Validated param validation check 할 때 사용
public class UserController extends BaseController<UserRequest.Base, UserResponse.Base, User> {

    @GetMapping("test/{id}")
    public ResponseEntity<User> testSelect(@PathVariable Long id){
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @GetMapping("test1/{id}")
    public Header<UserResponse.Base> test1Select(@PathVariable Long id){
        return baseService.read(id);
    }
}
