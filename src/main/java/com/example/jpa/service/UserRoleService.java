package com.example.jpa.service;

import com.example.jpa.pojo.UserRole;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRoleService {
    @NonNull
    Optional<UserRole> getByUserId(@NonNull Integer userId);

    @NonNull
    Optional<UserRole> getByRoleId(@NonNull Integer roleId);

    void setId(@NonNull int userId, @NonNull int roleId);
}
