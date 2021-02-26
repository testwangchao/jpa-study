package com.example.jpa.service;

import com.example.jpa.param.RoleInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.pojo.UserRole;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRoleService {
    @NonNull
    Optional<UserRole> getByUserId(@NonNull Integer userId);

    @NonNull
    Optional<List<UserRole>> getByRoleId(@NonNull Integer roleId);

    void setId(@NonNull int userId, @NonNull int roleId);

    /*
    find users by roleId
     */
    List<User> findUserByRoleId(@NonNull RoleInfo roleInfo);
}
