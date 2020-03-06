package com.gsm.service.impl;

import com.gsm.dao.UserDao;
import com.gsm.model.User;
import com.gsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * @Cacheable(value = "user",key = "#id")       使用该注解的方法会在执行后缓存结果
     * @CacheEvict(value="user",key="#id")          使用该注解的方法会在执行前后移除
     * @param id
     * @return
     */
    @Cacheable(value = "user",key = "#id")
    @Override
    public User selectUserById(String id) {
        User user = userDao.selectUserById(id);
        return user;
    }
}
