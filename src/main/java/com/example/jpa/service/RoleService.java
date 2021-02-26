package com.example.jpa.service;

import com.example.jpa.pojo.Role;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface RoleService {
    @NonNull
    Optional<Role> getByUsername(@NonNull String username);

    @NonNull
    Role getByUsernameNonNull(@NonNull String username);
}
