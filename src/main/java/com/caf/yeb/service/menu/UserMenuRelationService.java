package com.caf.yeb.service.menu;


import com.baomidou.mybatisplus.extension.service.IService;
import com.caf.yeb.beans.menu.model.UserMenuRelation;
import com.caf.yeb.beans.menu.response.UserMenuVO;

import java.io.Serializable;
import java.util.List;


/**
 * (UserMenuRelation)表服务接口
 *
 * @author chenhaohao
 * @since 2022-01-11 13:55:54
 */
public interface UserMenuRelationService extends IService<UserMenuRelation> {

    /**
     * 根据ID获取基本信息
     *
     * @param id 主键ID
     * @return {@link}
     * @author chenhaohao
     */
    @Override
    public UserMenuRelation getById(Serializable id);

    /**
     * 逻辑删除数据
     *
     * @param ids 主键集合
     * @return {@link boolean}
     * @author chenhaohao
     */
    public boolean removeByIds(String... ids);

    /**
     * 获取登录用户的额外权限菜单
     */
    List<UserMenuVO> getMenuListByLoginUser();

}
