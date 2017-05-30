package com.rickey.microservice.service.impl;

import com.rickey.microservice.model.User;
import com.rickey.microservice.repository.UserRepository;
import com.rickey.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rickey_17 on 17-5-13.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(int id) {
        return userRepository.findOne(id);
    }
}
