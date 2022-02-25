package com.caf.yeb.beans.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2022/1/11 14:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginParam {
    private String account;
    private String phone;
    private String password;
    private String captcha;
}
