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
 * (Menu)_实体类
 *
 * @author chenhaohao
 * @since 2022-01-11 13:55:49
 */
@Data
@NoArgsConstructor
@TableName(value = "menu")
public class Menu {

    /**
     * 菜单ID
     */
     @TableId(value = "menu_id", type = IdType.AUTO)
    private String menuId;

    /**
     * 
     */
     @TableField(value = "menu_uuid")
    private String menuUuid;

    /**
     * url
     */
     @TableField(value = "url")
    private String url;

    /**
     * path
     */
     @TableField(value = "path")
    private String path;

    /**
     * 组件
     */
     @TableField(value = "component")
    private String component;

    /**
     * 菜单名
     */
     @TableField(value = "menu_name")
    private String menuName;

    /**
     * 图标
     */
     @TableField(value = "icon")
    private String icon;

    /**
     * 是否激活  0：激活  1：不激活
     */
     @TableField(value = "is_activation")
    private Integer isActivation;

    /**
     * 父级ID
     */
     @TableField(value = "parent_id")
    private String parentId;

    /**
     * 深度  0：最上级  1：次级  以此类推
     */
     @TableField(value = "depth")
    private Integer depth;

    /**
     * 是否启用 0：启用  1：不启用
     */
     @TableField(value = "is_enable")
    private Integer isEnable;

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
