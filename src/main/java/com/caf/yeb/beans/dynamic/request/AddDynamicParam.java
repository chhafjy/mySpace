package com.caf.yeb.beans.dynamic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2022/1/18 17:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddDynamicParam {
    private String dynamicId;
    @NotBlank(message = "内容不能为空")
    private String content;

}
