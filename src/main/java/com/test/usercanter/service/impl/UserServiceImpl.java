package com.test.usercanter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.usercanter.model.domain.User;
import com.test.usercanter.service.UserService;
import com.test.usercanter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author tingting
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-08-30 23:31:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




