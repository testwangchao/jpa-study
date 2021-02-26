package com.example.jpa.service;

import com.example.jpa.param.RoleInfo;
import com.example.jpa.pojo.Role;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    @NonNull
    Optional<Role> getByUsername(@NonNull String username);

    @NonNull
    Role getByUsernameNonNull(@NonNull String username);

    void createRole(@NonNull RoleInfo roleInfo);

    List<Role> getAllRoles();
}
