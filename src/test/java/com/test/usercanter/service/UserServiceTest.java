package com.test.usercanter.service;
import java.util.Date;

import com.test.usercanter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("test1");
        user.setAvatarUrl("");
        user.setUserAccount("123");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("123");
        user.setEmail("456");
        user.setUserStatus(0);
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }
}