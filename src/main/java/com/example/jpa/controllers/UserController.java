package com.example.jpa.controllers;

import com.example.jpa.param.SetRole;
import com.example.jpa.param.UserInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.pojo.User2;
import com.example.jpa.service.UserRoleService;
import com.example.jpa.service.UserService;
import com.example.jpa.support.BaseResponse;
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
    private final UserService userService;
    private final UserRoleService userRoleService;
    public UserController(UserService userService, UserRoleService userRoleService){
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("userInfo")
    public User getUserInfo(@RequestBody UserInfo userInfo) {
        return userService.getByUsernameNonNull(userInfo.getUserName());
    }

    /*
    多个业务第一种方式：
    多个业务操作需要在controller添加Transactional；
    否则不会进行事务回滚
     */
    @Transactional
    @PostMapping("deleteUser")
    public int deleteUser(@RequestBody UserInfo userInfo) {
        Assert.notNull(String.valueOf(userInfo.getUserName()), "userId不能为空");
        userService.createUser(userInfo);
        userService.deleteByUserId(userInfo.getUserId());
        return 1;
    }

    /*
    多个业务第二种方式：
    多个业务操作，可以将业务组装在一起，
     */
    @PostMapping("deleteUser2")
    public int deleteUser2(@RequestBody UserInfo userInfo) {
        Assert.notNull(String.valueOf(userInfo.getUserName()), "userId不能为空");
        userService.deleteCreate(userInfo);
        return 1;
    }

    @GetMapping("userList")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("user2")
    public User2 getUser2(@RequestBody User user) {
        return userService.convertTo(user);
    }

    @PostMapping("create")
    public BaseResponse<String> createUser(@RequestBody UserInfo userInfo) {
        userService.createUser(userInfo);
        return BaseResponse.ok("change success");
    }

    @PostMapping("setRole")
    public void setRole(@RequestBody SetRole setRole){
        userRoleService.setId(setRole.getUserId(), setRole.getRoleId());
    }
}
