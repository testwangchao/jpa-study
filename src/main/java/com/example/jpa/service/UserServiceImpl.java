package com.example.jpa.service;
import com.example.jpa.dto.UserDto;
import com.example.jpa.event.user.UserCreateEvent;
import com.example.jpa.exceptions.NotFoundException;
import com.example.jpa.param.UserInfo;
import com.example.jpa.pojo.User;
import com.example.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    public UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(UserRepository userRepository, ApplicationEventPublisher eventPublisher){

        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User getByUsernameNonNull(String username) {
        return getByUsername(username).orElseThrow(() -> new NotFoundException("没有找到该用户！"));
    }

    @Override
    public List<User> getByUserId(List<Integer> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Transactional
    @Override
    public Optional<Integer> deleteByUserId(int userId) {

        try {
            userRepository.deleteById(userId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e){
            throw new NotFoundException("没有找到该用户！");
        }
        return Optional.of(1);
    }

    public void deleterUser(int userId){

        deleteByUserId(userId).orElseThrow(() -> new NotFoundException("没有找到该用户！"));
    }

    @Override
    public Page<User> getAllUsers(Sort sort) {
        Pageable pageable = PageRequest.of(1, 10, sort);
        Page<User> users = userRepository.findAll(pageable);
//        Map<String, Object> map = new HashMap<>();
//        map.put("content", users.getContent());
//        map.put("total", users.getTotalElements());
//        map.put("pages", users.getTotalPages());
//        return map;
        return users;
//        return userRepository.findAll(pageable);
//                .stream()
//                .sorted((u1, u2)->u2.getId()-u1.getId()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public User createUser(UserInfo userInfo) {
        User user = userInfo.convertTo();
        userRepository.save(user);
        eventPublisher.publishEvent(new UserCreateEvent(this, userInfo.getName(), userInfo));
        return user;
    }

    @Override
    public UserDto convertTo(User user) {
        Assert.notNull(user, "user must not be null!");
        System.out.println(new UserDto().convertFrom(user));
        return new UserDto().convertFrom(user);
    }

    @Override
    public void deleteCreate(UserInfo userInfo) {
        createUser(userInfo);
        deleteByUserId(userInfo.getId());
    }


}
