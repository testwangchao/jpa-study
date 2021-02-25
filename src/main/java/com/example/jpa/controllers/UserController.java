package com.example.jpa.controllers;

import com.example.jpa.param.UserInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private  UserService userService;


    @PostMapping("userInfo")
    public User getUserInfo(@RequestBody UserInfo userInfo) {
        return userService.getByUsernameNonNull(userInfo.getUserName());
    }

    @Transactional
    @PostMapping("deleteUser")
    public int deleteUser(@RequestBody UserInfo userInfo) {
        Assert.notNull(String.valueOf(userInfo.getUserName()), "userId不能为空");
        userService.createUser(userInfo);
        userService.deleteByUserId(userInfo.getUserId());
        return 1;
    }


    @GetMapping("userList")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
