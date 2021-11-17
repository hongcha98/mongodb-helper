package com.hongcha.mongodb.core.service.impl;

import com.hongcha.mongodb.core.service.UserService;
import com.hongcha.mongodb.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseMongoServiceImpl<User> implements UserService {
    public UserServiceImpl() {
        super("user", User.class);
    }
}
