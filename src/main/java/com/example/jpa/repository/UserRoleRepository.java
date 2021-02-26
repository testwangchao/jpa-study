package com.example.jpa.repository;

import com.example.jpa.pojo.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @NonNull
    Optional<UserRole> findByUserId(@NonNull Integer userId);

    @NonNull
    Optional<UserRole> findByRoleId(@NonNull Integer roleId);
}
