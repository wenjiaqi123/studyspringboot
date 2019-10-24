package com.gsm.dao;

import com.gsm.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Mapper 是Mybatis框架提供
 * @Repository 是Spring框架提供
 */
@Repository
public interface UserDao {
    List<User> selectUsers();

    void insertUser();

    void updateUser();
}
