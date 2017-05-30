package com.rickey.microservice.service;


import com.rickey.microservice.model.User;

/**
 * Created by Rickey_17 on 17-5-13.
 */
public interface UserService {

    User getById(int id);
}
