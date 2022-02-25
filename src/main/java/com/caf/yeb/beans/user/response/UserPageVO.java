package com.caf.yeb.beans.user.response;

import com.caf.yeb.common.pojo.PageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPageVO {
    private List<UserPageDTO> list;

    private PageVO page;
}
