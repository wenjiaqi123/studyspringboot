package com.gsm.service;

import com.gsm.model.User;

import java.util.List;

public interface UserService {
    List<User> selectUsers();

    void insertUser();

    User selectUserById(String id);
}
