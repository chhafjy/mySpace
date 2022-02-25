package com.caf.yeb.service.menu;


import com.baomidou.mybatisplus.extension.service.IService;
import com.caf.yeb.beans.menu.model.Menu;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * (Menu)表服务接口
 *
 * @author chenhaohao
 * @since 2022-01-11 13:55:51
 */
public interface MenuService extends IService<Menu> {
    
    /**
     * 根据ID获取基本信息
     *
     * @param id 主键ID
     * @return {@link}
     * @author chenhaohao
     */
     @Override
    public Menu getById(Serializable id);

    /**
     * 根据ID获取基本信息
     *
     * @param ids 主键IDs
     * @return {@link}
     * @author chenhaohao
     */
    public List<Menu> getByIds(Set<String> ids);
    
    /**
     * 逻辑删除数据
     *
     * @param ids 主键集合
     * @return {@link boolean}
     * @author chenhaohao
     */
    public boolean removeByIds(String... ids);
    
}
