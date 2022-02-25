package com.caf.yeb.common.util.bean;

import lombok.extern.slf4j.Slf4j;

import static cn.hutool.core.util.IdUtil.getSnowflake;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 */
@Slf4j
public class IdUtils {

    private static Integer workerId = 1;

    private static Integer dataCenterId = 1;

    /**
     * twitter 雪花ID
     *
     * @author qinxx
     * @date 2021/4/25 6:01 下午
     * @return {@link long}
     */
    public static long getId() {
        return getSnowflake(workerId, dataCenterId).nextId();
    }

    /**
     * 雪花ID字符串
     *
     * @author qinxx
     * @date 2021/4/25 6:01 下午
     * @return {@link String}
     */
    public static String getStrId() {
        return String.valueOf(getId());
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            System.out.println(getId());
        }
    }
}
