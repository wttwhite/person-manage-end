package com.test.usercanter.service;

import com.test.usercanter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author tingting
* @description 针对表【user】的数据库操作Service
* @createDate 2024-08-30 23:31:39
*/
public interface UserService extends IService<User> {
    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
}
