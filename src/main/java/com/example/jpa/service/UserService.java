package com.example.jpa.service;

import com.example.jpa.dto.UserDto;
import com.example.jpa.param.UserInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends CrudService<User, Integer> {
    @NonNull
    Optional<User> getByUsername(@NonNull String username);

    @NonNull
    User getByUsernameNonNull(@NonNull String username);

    @NonNull
    List<User> getByUserId(@NonNull List<Integer> userIds);

    @NonNull
    Optional<Integer> deleteByUserId(@NonNull int userId);

    void deleterUser(@NonNull int userId);

    @NonNull
    Page<User> getAllUsers(Sort sort);

    @NonNull
    User createUser(@NonNull UserInfo userInfo);

    @NonNull
    UserDto convertTo(@NonNull User user);

    @Transactional
    void deleteCreate(@NonNull UserInfo userInfo);


}