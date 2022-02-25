package com.caf.yeb.beans.user.constact;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2022/1/11 14:09
 */
public enum UserStatusEnum {
    NORMAL("正常", 0),
    LOCKED("锁定", 1)
    ;

    private String name;
    private Integer value;

    UserStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
