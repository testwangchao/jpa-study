package com.example.jpa.service;

import com.example.jpa.pojo.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserService {
    @NonNull
    Optional<User> getByUsername(@NonNull String username);
}
