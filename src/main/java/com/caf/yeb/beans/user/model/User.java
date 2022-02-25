package com.caf.yeb.beans.user.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (User)_实体类
 *
 * @author chenhaohao
 * @since 2022-01-11 13:59:03
 */
@Data
@NoArgsConstructor
@TableName(value = "user")
public class User {

    /**
     * 
     */
     @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 用户UUID
     */
     @TableField(value = "user_uuid")
    private String userUuid;

    /**
     * 
     */
     @TableField(value = "user_name")
    private String userName;

    /**
     * 
     */
     @TableField(value = "account")
    private String account;

     /**
     *
     */
     @TableField(value = "modify_account")
    private Integer modifyAccount;

    /**
     * 
     */
     @TableField(value = "phone")
    private String phone;

    /**
     * 
     */
     @TableField(value = "password")
    private String password;

    /**
     * 类型：
0：超级管理员  1：管理员  2：普通成员
     */
     @TableField(value = "type")
    private Integer type;

    /**
     * 头像
     */
     @TableField(value = "avatar_file_no")
    private String avatarFileNo;

    /**
     * 备注
     */
     @TableField(value = "description")
    private String description;

    /**
     * 用户状态 0：正常  1锁定
     */
     @TableField(value = "status")
    private Integer status;

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
