package com.caf.yeb.service.menu.impl;

import com.caf.yeb.beans.menu.model.Menu;
import com.caf.yeb.beans.menu.response.UserMenuVO;
import com.caf.yeb.common.util.user.UserHelper;
import com.caf.yeb.dao.menu.UserMenuRelationDao;
import com.caf.yeb.beans.menu.model.UserMenuRelation;
import com.caf.yeb.service.menu.MenuService;
import com.caf.yeb.service.menu.UserMenuRelationService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (UserMenuRelation)表服务实现类
 *
 * @author chenhaohao
 * @since 2022-01-11 13:55:55
 */
@Service
@Slf4j
public class UserMenuRelationServiceImpl extends ServiceImpl<UserMenuRelationDao, UserMenuRelation> implements UserMenuRelationService {


    @Resource
    UserHelper userHelper;

    @Resource
    MenuService menuService;

    /**
     * 复写基类通过ID获取详情数据
     *
     * @param id 主键ID
     * @return 数据库对象数据
     */
    @Override
    public UserMenuRelation getById(Serializable id) {
        return lambdaQuery().eq(UserMenuRelation::getRelationId, id).eq(UserMenuRelation::getDeleteFlag, 0).last("limit 1").one();
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
        return lambdaUpdate().set(UserMenuRelation::getDeleteFlag, 1)
                .in(UserMenuRelation::getRelationId, idsList).update();
    }

    /**
     * 获取登录用户的额外权限菜单
     */
    @Override
    public List<UserMenuVO> getMenuListByLoginUser() {
        //获取登录用户ID
        String userId = userHelper.getUserId();
        log.info("login userId is {}", userId);
        if(StringUtils.isBlank(userId)){
            return Collections.emptyList();
        }
        //获取用户的菜单ID
        List<UserMenuRelation> list = lambdaQuery().eq(UserMenuRelation::getUserId, userId).eq(UserMenuRelation::getDeleteFlag,0).list();
        if(CollectionUtils.isEmpty(list)){
            log.info("用户没有额外菜单 用户ID是 {}", userId);
            return Collections.emptyList();
        }
        //获取菜单信息
        Set<String> menuIds = list.stream().map(UserMenuRelation::getMenuId).collect(Collectors.toSet());
        List<Menu> menus = menuService.getByIds(menuIds);
        if(CollectionUtils.isEmpty(menus)){
            return Collections.emptyList();
        }
        List<UserMenuVO> listVO = new ArrayList<>();
        menus.forEach(s -> {
            UserMenuVO build = UserMenuVO.builder().name(s.getMenuName()).path(s.getPath()).icon(s.getIcon()).build();
            listVO.add(build);
        });
        return listVO;
    }
}
