package com.example.jpa.controllers;

import com.example.jpa.param.RoleInfo;
import com.example.jpa.pojo.Role;
import com.example.jpa.service.RoleService;
import com.example.jpa.support.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping("create")
    public BaseResponse<String> createRole(@RequestBody RoleInfo roleInfo){
        roleService.createRole(roleInfo);
        return BaseResponse.ok("创建成功");
    }

    @GetMapping("roleList")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
}
