package com.caf.yeb.beans.dynamic.response;

import com.caf.yeb.common.pojo.PageVO;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * @version 1.0
 * @author: chenhaohao
 * @date 2022/3/15 22:45
 */
@Data
@Builder
public class AllDynamicVO {

    private List<AllDynamic> list;

    private PageVO page;

    @Tolerate
    public AllDynamicVO() {
    }

    @Data
    @Builder
    public static class AllDynamic {

        /**
         * 动态ID
         */
        private String dynamicId;

        /**
         * 内容
         */
        private String content;

        /**
         * 浏览次数
         */
        private Integer viewCount;

        /**
         * 发表人姓名
         */
        private String userName;

        /**
         * 头像
         */
        private String avatar;

        /**
         * 是否收藏 0否 1是
         */
        private Integer isFavorites;

        /**
         * 收藏图标
         */
        private String favoriteIcon;

        @Tolerate
        public AllDynamic() {
        }
    }
}
