package com.test.usercanter;

import com.test.usercanter.mapper.UserMapper;
import com.test.usercanter.model.User;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCanterApplicationTests {

//        @Autowired  // 按照类型去注入
    @Resource // 按照java bin的名称注入
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(1 ,userList.size());
        userList.forEach(System.out::println);
    }

}