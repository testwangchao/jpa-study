package com.example.jpa.service;

import com.example.jpa.exceptions.NotFoundException;
import com.example.jpa.pojo.User;
import com.example.jpa.pojo.UserRole;
import com.example.jpa.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;
    private final UserService userService;
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserService userService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Override
    public Optional<UserRole> getByUserId(Integer userId) {
        return userRoleRepository.findByUserId(userId);
    }

    @Override
    public List<UserRole> getByRoleId(Integer roleId) {
        return userRoleRepository.findByRoleId(roleId);
    }

    @Override
    public void setId(int userId, int roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleRepository.save(userRole);
    }

    @Override
    public List<User> findUsersRole(int roleId) {
        List<Integer> userIds = getUserRoleByRoleId(roleId).stream().
                map(UserRole::getUserId).
                collect(Collectors.toList());
        return userService.getByUserId(userIds);
    }

    public List<UserRole> getUserRoleByRoleId(int roleId) {
//        return getByRoleId(roleId).orElseThrow(() -> new NotFoundException(String.format("角色ID: %s未查询到数据", roleId)));
        return userRoleRepository.findByRoleId(roleId);
    }

}
