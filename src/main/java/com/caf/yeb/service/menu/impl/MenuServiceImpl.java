package com.caf.yeb.service.menu.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caf.yeb.beans.menu.model.Menu;
import com.caf.yeb.dao.menu.MenuDao;
import com.caf.yeb.service.menu.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Menu)表服务实现类
 *
 * @author chenhaohao
 * @since 2022-01-11 13:55:52
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {


    /**
     * 复写基类通过ID获取详情数据
     *
     * @param id 主键ID
     * @return 数据库对象数据
     */
    @Override
    public Menu getById(Serializable id) {
        return lambdaQuery().eq(Menu::getMenuId, id).eq(Menu::getDeleteFlag, 0).last("limit 1").one();
    }

    /**
     * 复写基类通过ID获取详情数据
     *
     * @param ids 主键IDs
     * @return 数据库对象数据
     */
    @Override
    public List<Menu> getByIds(Set<String> ids) {
        return lambdaQuery().in(Menu::getMenuId, ids).eq(Menu::getDeleteFlag, 0).list();
    }

    /**
     * 通过ID集合删除
     *
     * @param ids 主键集合
     * @return 成功与否
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(String... ids) {
        List<String> idsList = Arrays.stream(ids).collect(Collectors.toList());
        //逻辑删除
        return lambdaUpdate().set(Menu::getDeleteFlag, 1)
                .in(Menu::getMenuId, idsList).update();
    }
    
    
}
