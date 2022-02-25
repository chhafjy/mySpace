package com.caf.yeb.common.constact;

/**
 * @author chenhaohao
 * @version 1.0
 */
public enum LoginEnum implements BaseEnum{
    LOGIN_SUCCESS(3020, "登录成功"),
    TOKEN_ERROR(30021, "登录失效，请重新登录"),

    ;
    private Integer code;
    private String message;

    LoginEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
