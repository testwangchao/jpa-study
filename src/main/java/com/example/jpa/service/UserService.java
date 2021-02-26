package com.example.jpa.service;

import com.example.jpa.param.RoleInfo;
import com.example.jpa.param.UserInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.pojo.User2;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserService {
    @NonNull
    Optional<User> getByUsername(@NonNull String username);

    @NonNull
    User getByUsernameNonNull(@NonNull String username);

    @NonNull
    Optional<Integer> deleteByUserId(@NonNull int userId);

    void deleterUser(@NonNull int userId);

    @NonNull
    List<User> getAllUsers();

    void createUser(@NonNull UserInfo userInfo);

    User2 convertTo(User user);

    void deleteCreate(@NonNull UserInfo userInfo);


}