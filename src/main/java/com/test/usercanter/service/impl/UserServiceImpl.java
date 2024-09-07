package com.test.usercanter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.usercanter.model.domain.User;
import com.test.usercanter.service.UserService;
import com.test.usercanter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.test.usercanter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
* @author tingting
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-08-30 23:31:39
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;

    private static final String validPatten = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    private static final String SALT = "1234567890";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword){
        // 1. 校验
        // a. 非空
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        // b. 账号不小于4位
        if (userAccount.length() < 4) {
            return -1;
        }
        // c. 密码不小于8位
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        // d. 账户不包含特殊字符
        Matcher matcher = Pattern.compile(validPatten).matcher(userAccount);
        if(matcher.find()){
            return -1;
        }
        // e. 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 这一个校验放最后，节省查询数据库的浪费
        // f. 账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(userQueryWrapper);
        System.out.println("count:"+ count);
        if (count > 0) {
            return -1;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean result = this.save(user);
        System.out.println("result:"+ result);
        return !result ? -1 : user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword , HttpServletRequest request) {
        // 1. 校验
        // a. 非空
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        // b. 账号不小于4位
        if (userAccount.length() < 4) {
            return null;
        }
        // c. 密码不小于8位
        if (userPassword.length() < 8) {
            return null;
        }
        // d. 账户不包含特殊字符
        Matcher matcher = Pattern.compile(validPatten).matcher(userAccount);
        if(matcher.find()){
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        userQueryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        // 账号或密码不对 --- 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }


    @Override
    public User getSafetyUser(User originalUser){
        User safetyUser = new User();
        safetyUser.setId(originalUser.getId());
        safetyUser.setUsername(originalUser.getUsername());
        safetyUser.setAvatarUrl(originalUser.getAvatarUrl());
        safetyUser.setUserAccount(originalUser.getUserAccount());
        safetyUser.setGender(originalUser.getGender());
        // safetyUser.setUserPassword("");  不需要返回给前端
        safetyUser.setPhone(originalUser.getPhone());
        safetyUser.setEmail(originalUser.getEmail());
        safetyUser.setUserStatus(originalUser.getUserStatus());
        safetyUser.setCreateTime(originalUser.getCreateTime());
        safetyUser.setUserRole(originalUser.getUserRole());
        // safetyUser.setUpdateTime(new Date());
        // safetyUser.setIsDelete(0);
        return safetyUser;
    }
}




