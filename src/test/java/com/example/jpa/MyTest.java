package com.example.jpa;

import com.example.jpa.param.UserInfo;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void test1() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        System.out.println(userInfo.getUserId());
    }
}
