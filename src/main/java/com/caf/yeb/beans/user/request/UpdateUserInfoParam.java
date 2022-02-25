package com.caf.yeb.beans.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author chenhaohao
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoParam {

    @NotBlank(message = "用户ID不能为空")
    private String userId;

    private String userName;

    private String phone;

    private String account;

}
