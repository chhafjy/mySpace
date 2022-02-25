package com.caf.yeb.common.constact;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 */
public enum CommonEnum implements BaseEnum{

    SUCCESS(200,"操作成功"),
    FAIL(500,"操作失败"),
    PARAM_ERROR(3003,"参数校验错误"),
    IGNORE(3001, "忽略消息提示框"),
    ;
    private Integer code;
    private String message;

    CommonEnum(Integer code, String message) {
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
