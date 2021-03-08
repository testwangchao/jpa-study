package com.example.jpa.controllers;

import com.example.jpa.dto.UserDto;
import com.example.jpa.param.RoleInfo;
import com.example.jpa.param.SetRole;
import com.example.jpa.param.UserInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.service.UserRoleService;
import com.example.jpa.service.UserService;
import com.example.jpa.utils.JsonUtils;
import com.example.jpa.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

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
        return userService.getByUsernameNonNull(userInfo.getName());
    }

    /*
    多个业务第一种方式：
    多个业务操作需要在controller添加Transactional；
    否则不会进行事务回滚
     */
    @Transactional
    @PostMapping("deleteUser")
    public int deleteUser(@RequestBody UserInfo userInfo) {
        Assert.notNull(String.valueOf(userInfo.getName()), "userId不能为空");
        userService.createUser(userInfo);
        userService.deleteByUserId(userInfo.getId());
        return 1;
    }

    /*
    多个业务第二种方式：
    多个业务操作，可以将业务组装在一起，
     */
    @PostMapping("deleteUser2")
    public int deleteUser2(@RequestBody UserInfo userInfo) {
        Assert.notNull(String.valueOf(userInfo.getName()), "userId不能为空");
        userService.deleteCreate(userInfo);
        return 1;
    }

    @GetMapping("userList")
    public Page<User> getAllUsers(){
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "id");
        List<Sort.Order> orders = new ArrayList<>(Arrays.asList(order1, order2));
        return userService.getAllUsers(Sort.by(orders));
    }

    @PostMapping("create")
    public UserDto createUser(@RequestBody @Validated UserInfo userInfo) {
        User user = userService.createUser(userInfo);
        return userService.convertTo(user);
//        return BaseResponse.ok("change success");
    }

    @PostMapping("setRole")
    public void setRole(@RequestBody SetRole setRole){
        userRoleService.setId(setRole.getUserId(), setRole.getRoleId());
    }

    @PostMapping("users")
    public List<User> getAllUersRole(@RequestBody RoleInfo roleInfo){
        return userRoleService.findUsersRole(roleInfo.getRoleId());
    }

//    @GetMapping("client")
//    public BaseResponse<Map<String,Object>> client() throws JsonProcessingException {
//        Map<String,Object> result = myClient.helloForest();
//        Map<String,Object> resp = new HashMap<>();
//        resp.put("content", result);
////        System.out.println(JsonUtils.jsonToMap(result).getClass());
//        return BaseResponse.ok(resp);
//    }

    @PostMapping("setStatus")
//    @NeedManagerPower(mode = 1)
    public void setStatus(@RequestBody UserInfo userInfo){

        userService.setUserStatus(userInfo);
    }

    @PostMapping("sendform")
    public Map<String, Object> sendRequest(@RequestBody Map<String,Object> map) throws IOException {
        return JsonUtils.jsonToMap(OkHttpUtils.postForm(map).body().string());
    }
}
