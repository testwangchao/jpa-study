package com.example.jpa.service;

import com.example.jpa.pojo.User;
import com.example.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByName(username);
    }
}
