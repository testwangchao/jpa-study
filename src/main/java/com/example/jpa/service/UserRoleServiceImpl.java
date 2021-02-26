package com.example.jpa.service;

import com.example.jpa.pojo.UserRole;
import com.example.jpa.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;
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

    @Override
    public void setId(int userId, int roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleRepository.save(userRole);
    }

}
