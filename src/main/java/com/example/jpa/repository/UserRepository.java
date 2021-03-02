package com.example.jpa.repository;

import com.example.jpa.pojo.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Integer> {
    @NonNull
    Optional<User> findByName(@NonNull String username);
}

