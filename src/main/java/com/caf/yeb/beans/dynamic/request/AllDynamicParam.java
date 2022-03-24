package com.caf.yeb.beans.dynamic.request;

import com.caf.yeb.common.pojo.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @author: chenhaohao
 * @date 2022/3/15 22:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllDynamicParam {

    private String dynamicName;

    /**
     * 是否我的动态  0否 1是
     */
    private Integer isMy;

    private PageParam page;
}
