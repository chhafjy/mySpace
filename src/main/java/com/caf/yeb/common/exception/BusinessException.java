package com.caf.yeb.common.exception;


import com.caf.yeb.common.constact.BaseEnum;
import com.caf.yeb.common.constact.CommonEnum;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 业务异常
 *
 * @author youma
 * @version 1.0
 * @date 2021/4/22 3:06 下午
 */
public class BusinessException extends RuntimeException implements BaseEnum {

    private Integer code;

    private String message;

    public BusinessException(BaseEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public BusinessException(BaseEnum baseEnum, String... msg) {
        this.code = baseEnum.getCode();
        this.message = Arrays.stream(msg).collect(Collectors.joining());
    }

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public BusinessException(String msg) {
        this.code = CommonEnum.FAIL.getCode();
        this.message = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
