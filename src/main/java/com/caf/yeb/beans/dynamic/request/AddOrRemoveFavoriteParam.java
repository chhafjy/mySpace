package com.caf.yeb.beans.dynamic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @author: chenhaohao
 * @date 2022/3/19 0:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddOrRemoveFavoriteParam {

    /**
     * 动态ID
     */

    private String dynamicId;

    /**
     * 是否收藏 0-否 1-是
     */
    private Integer isFavorite;
}
