package com.caf.yeb.beans.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2022/1/11 14:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfoVO {

    private String userId;
    private String userName;
    private String account;
    private String phone;
    private Integer modifyAccount;
    private Integer type;
    private String avatar;
    private String description;

}
