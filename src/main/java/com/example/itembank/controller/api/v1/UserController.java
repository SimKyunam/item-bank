package com.example.itembank.controller.api.v1;

import com.example.itembank.controller.BaseController;
import com.example.itembank.model.entity.User;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.UserResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@Slf4j
@RestController
@RequestMapping("/v1/api/user")
//@Validated param validation check 할 때 사용
public class UserController extends BaseController<UserRequest.Base, UserResponse.Base, User> {

}
