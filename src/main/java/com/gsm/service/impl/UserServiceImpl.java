package com.gsm.service.impl;

import com.gsm.dao.UserDao;
import com.gsm.model.User;
import com.gsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> selectUsers() {
        return userDao.selectUsers();
    }

    @Override
    public void insertUser() {
        userDao.insertUser();
        userDao.updateUser();
    }
}
