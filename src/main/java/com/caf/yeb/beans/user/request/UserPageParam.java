package com.caf.yeb.beans.user.request;

import com.caf.yeb.common.pojo.PageParam;
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
public class UserPageParam {

    private String userName;

    private PageParam page;
}
