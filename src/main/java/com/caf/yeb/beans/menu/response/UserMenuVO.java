package com.caf.yeb.beans.menu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UserMenuVO {

    private String name;

    private String icon;

    private String path;

}
