package com.example.jpa.service;

import com.example.jpa.pojo.UserRole;
import com.example.jpa.repository.UserRoleRepository;

import java.util.Optional;

public class UserRoleServiceImpl implements UserRoleService{

    private UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Optional<UserRole> getByUserId(Integer userId) {
        return userRoleRepository.findByUserId(userId);
    }

    @Override
    public Optional<UserRole> getByRoleId(Integer roleId) {
        return userRoleRepository.findByRoleId(roleId);
    }
}
