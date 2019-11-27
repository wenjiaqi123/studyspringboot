package com.gsm.controller;

import com.gsm.model.User;
import com.gsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<User> selectUsers(Integer id){
        return userService.selectUsers();
    }

    @PostMapping("insertUser")
    public String insertUser(User user){
        System.out.println("---------------");
        System.out.println(user.getUserName());
        userService.insertUser();
        return "ss";
    }
}
