package com.example.jpa.service;

import com.example.jpa.exceptions.NotFoundException;
import com.example.jpa.param.RoleInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.pojo.UserRole;
import com.example.jpa.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Optional<List<UserRole>> getByRoleId(Integer roleId) {
        return userRoleRepository.findAllById(roleId);
    }

    @Override
    public void setId(int userId, int roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> findUserByRoleId(int roleId) {
        int userId = getUserRoleByRoleId(roleId).getUserId();
        return userRoleRepository.fin;
    }

    public UserRole getUserRoleByRoleId(int roleId) {
        return getByRoleId(roleId).orElseThrow(() -> new NotFoundException(String.format("角色ID: %s不存在", roleId)));
    }

}
