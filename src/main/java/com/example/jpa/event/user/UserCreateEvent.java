package com.example.jpa.event.user;

import com.example.jpa.param.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

@Slf4j
public class UserCreateEvent extends ApplicationEvent {
    private final UserInfo userInfo;

    public UserCreateEvent(Object source, @NonNull String userName, UserInfo userInfo) {
        super(source);
        this.userInfo = userInfo;
        log.info("新建用户：{}", userName);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
