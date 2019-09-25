package com.gsm.dao;

import com.gsm.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> selectUsers();
}
