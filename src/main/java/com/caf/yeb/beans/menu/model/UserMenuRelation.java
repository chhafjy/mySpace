package com.caf.yeb.beans.menu.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (UserMenuRelation)_实体类
 *
 * @author chenhaohao
 * @since 2022-01-11 13:55:53
 */
@Data
@NoArgsConstructor
@TableName(value = "user_menu_relation")
public class UserMenuRelation {

    /**
     * 
     */
     @TableId(value = "relation_id", type = IdType.AUTO)
    private String relationId;

    /**
     * 
     */
     @TableField(value = "user_id")
    private String userId;

    /**
     * 
     */
     @TableField(value = "menu_id")
    private String menuId;

    /**
     * 删除标志 0:有效， 1:无效
     */
     @TableField(value = "delete_flag")
    private Integer deleteFlag;

    /**
     * 创建时间
     */
     @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
     @TableField(value = "modify_time")
    private Date modifyTime;

}
