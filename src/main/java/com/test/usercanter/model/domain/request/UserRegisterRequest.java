package com.test.usercanter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * 序列化
 */
@Data  // 生成get set方法
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;


}


