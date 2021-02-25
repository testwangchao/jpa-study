package com.example.jpa;

import com.example.jpa.pojo.User;
import com.example.jpa.repository.UserRepository;
import com.example.jpa.service.UserService;
//import com.example.jpa.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class JpaApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        User user = new User();
        user.setName("dasd");
        user.setPassword("123456");
        user.setBirthday("2008-08-08");
        userRepository.save(user);

    }

    @Test
    public void test1(){
        System.out.println(userRepository.findAll());
    }

}
