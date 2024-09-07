package com.test.usercanter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.usercanter.model.domain.User;
import com.test.usercanter.model.domain.request.UserLoginRequest;
import com.test.usercanter.model.domain.request.UserRegisterRequest;
import com.test.usercanter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.test.usercanter.constant.UserConstant.ADMIN_ROLE;
import static com.test.usercanter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public long UserRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return 0;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return 0;
        }
        return userService.userRegister(userAccount,userPassword,checkPassword);
    }

    @PostMapping("/login")
    public User UserLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount,userPassword, request);
    }

    @GetMapping("/search")
    public List<User> UserSearch(@RequestParam String userAccount, HttpServletRequest request) {
        // 仅管理员可以查看
        if (isAdmin(request)) {
            return new ArrayList<User>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.like("userAccount", userAccount);
        }
        List<User> list = userService.list(queryWrapper);
        // user list 转换成数据流，遍历每个元素，给密码置空，再拼成一个完整的list
        return list.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean UserDelete(@RequestBody long id, HttpServletRequest request) {
        if (id <= 0 || !isAdmin(request)) {
            return false;
        }
        return userService.removeById(id); // 逻辑删除
    }

    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可以查看
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return  user == null || user.getUserRole() != ADMIN_ROLE;
    }
}
