package com.gsm.controller;

import com.gsm.model.User;
import com.gsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private UserService userService;

    @RequestMapping("selectUsers")
    public List<User> selectUsers(){
        return userService.selectUsers();
    }
}
