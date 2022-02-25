package com.caf.yeb.beans.dynamic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @version 1.0
 * @author: chenhaohao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThumbsUpParam {

    @NotBlank(message = "动态ID不能为空")
    private String dynamicId;

    /**
     * 0-点赞  1-取消点赞
     */
    @NotNull(message = "操作类型不能为空")
    private Integer isThumbsUp;
}
