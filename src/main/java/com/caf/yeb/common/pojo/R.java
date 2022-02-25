package com.caf.yeb.common.pojo;

import com.caf.yeb.common.constact.BaseEnum;
import com.caf.yeb.common.constact.CommonEnum;

import java.util.Collections;
import java.util.Map;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 */
public class R<T> {
    private Integer code;
    private String message;
    private T data;
    private Map<String, String> error;

    public R(){
        this.code = CommonEnum.SUCCESS.getCode();
    }

    private R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = Collections.emptyMap();
    }

    private R(BaseEnum e, T data) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.data = data;
        this.error = Collections.emptyMap();
    }

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.error = Collections.emptyMap();
    }

    private R(Integer code, String message, T data, Map<String, String> error) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public R(BaseEnum e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.error = Collections.emptyMap();
    }

    public static <T> R<T> error() {
        return new R(CommonEnum.FAIL);
    }

    public static <T> R<T> error(String msg) {
        return new R(CommonEnum.FAIL.getCode(), msg);
    }

    public static <T> R<T> error(CommonEnum exceptionEnum) {
        return new R(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    public static <T> R<T> paramError(Map<String, String> error) {
        return new R(CommonEnum.FAIL.getCode(), "请求参数错误", (Object) null, error);
    }

    public static <T> R<T> success() {
        return new R<T>(CommonEnum.SUCCESS);
    }

    public static <T> R<T> success(T t) {
        return new R<T>(CommonEnum.SUCCESS, t);
    }

    public static <T> R<T> success(BaseEnum e,T t) {
        return new R<T>(e, t);
    }

    public static <T> R<T> successV2(T t) {
        return new R<T>(CommonEnum.IGNORE, t);
    }

    public boolean isSuccess() {
        return CommonEnum.SUCCESS.getCode().equals(this.code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }
}
