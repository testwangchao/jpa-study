package com.example.jpa.service;
import com.example.jpa.exceptions.NotFoundException;
import com.example.jpa.param.UserInfo;
import com.example.jpa.pojo.User;
//import org.springframework.beans.factory.annotation.Autowired;
import com.example.jpa.pojo.User2;
import com.example.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
//    @Autowired
    public UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User getByUsernameNonNull(String username) {
        return getByUsername(username).orElseThrow(() -> new NotFoundException("没有找到该用户！"));
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void createUser(UserInfo userInfo) {
        User user = new User();
        user.setName(userInfo.getUserName());
        userRepository.save(user);

    }

    @Override
    public User2 convertTo(User user) {
        User2 user2 = new User2();
        User user1 = getByUsernameNonNull(user.getName());
        org.springframework.beans.BeanUtils
                .copyProperties(user1, user2);
        return user2;
    }

    @Transactional
    @Override
    public void deleteCreate(UserInfo userInfo) {
        createUser(userInfo);
        deleteByUserId(userInfo.getUserId());
    }
}
