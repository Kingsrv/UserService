package com.saurav.user.service.services;

import com.saurav.user.service.entities.User;

import java.util.List;

public interface UserService {

    //user operations
    //create
    User saveUser(User user);

    List<User> getAllUsers();

    User getUser(String userId);

    //TODO: delete and update
}
