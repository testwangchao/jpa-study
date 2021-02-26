package com.example.jpa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.example.jpa.aops.NeedManagerPower;
import com.example.jpa.pojo.User;
import com.example.jpa.service.UserService;
import com.example.jpa.utils.Result;
import com.example.jpa.utils.ResultUtils;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("user")
    @NeedManagerPower
    public String getUser(@RequestBody JSONObject param) {
        System.out.println("====run====");
        String userName = (String) param.get("name");
        User user = userService.getByUsernameNonNull(userName);
        return ResultUtils.success(user);

//        return userService.getByUsername("dasd");
    }
}
