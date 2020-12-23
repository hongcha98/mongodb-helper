package com.hongcha.mongodb.service.impl;

import com.hongcha.mongodb.domain.User;
import com.hongcha.mongodb.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseMongoServiceImpl<User> implements UserService {
    public UserServiceImpl() {
        super("user", User.class);
    }
}
