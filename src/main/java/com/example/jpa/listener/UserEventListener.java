package com.example.jpa.listener;

import com.example.jpa.event.user.UserCreateEvent;
import com.example.jpa.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventListener {
    @EventListener
    @Async
    public void onApplicationEvent(UserCreateEvent event) {
        // Convert to log
        User logToCreate = event.getUserInfo().convertTo();

        // Create log
        log.info("UserEventListener: {}", logToCreate);
    }
}
