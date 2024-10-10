package com.test.usercanter.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private String description;
    private T data;

    public BaseResponse(int code,  T data, String message, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }
    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
