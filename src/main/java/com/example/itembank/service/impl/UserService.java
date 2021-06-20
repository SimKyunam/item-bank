package com.example.itembank.service.impl;

import com.example.itembank.model.entity.User;
import com.example.itembank.model.network.Header;
import com.example.itembank.model.network.request.UserRequest;
import com.example.itembank.model.network.response.UserResponse;
import com.example.itembank.service.BaseService;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class UserService extends BaseService<UserRequest, UserResponse, User> {

    @Override
    public Header<UserResponse> create(Header<UserRequest> request) {
        return null;
    }

    @Override
    public Header<UserResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<UserResponse> update(Header<UserRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    @Override
    public Header<List<UserResponse>> search(Pageable pageable) {
        return null;
    }
}
