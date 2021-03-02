package com.example.jpa.repository;

import com.example.jpa.pojo.Role;
import com.example.jpa.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Integer> {

    @NonNull
    Optional<Role> findByName(@NonNull String roleName);
}
