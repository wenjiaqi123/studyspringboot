package com.gsm.controller;

import com.gsm.model.User;
import com.gsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController = @Controller + @ResponseBody
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private UserService userService;

    /**
     * @GetMapping = @RequestMapping(method = RequestMethod.GET)
     * @return
     */
    @GetMapping("selectUsers")
    public List<User> selectUsers(){
        return userService.selectUsers();
    }

    @PostMapping("insertUser")
    public String insertUser(){
        userService.insertUser();
        return "ss";
    }
}
