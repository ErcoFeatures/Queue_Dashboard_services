package com.spring.rest.webservices.restwebservices.service;

import com.spring.rest.webservices.restwebservices.model.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);
}