package com.caf.yeb.beans.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author chenhaohao
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDTO {

    private String userId;

    private String userName;

    private String phone;

    private String icon;

    private String account;
}
