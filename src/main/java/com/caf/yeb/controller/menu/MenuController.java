package com.caf.yeb.controller.menu;

import com.caf.yeb.beans.menu.response.UserMenuVO;
import com.caf.yeb.common.pojo.R;
import com.caf.yeb.common.util.redis.RedisUtils;
import com.caf.yeb.service.menu.UserMenuRelationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenhaohao
 * @version 1.0
 */
@RestController
public class MenuController {

    @Resource
    UserMenuRelationService userMenuRelationService;

    @Resource
    RedisUtils redisUtils;

    /**
     * 获取用户菜单
     */
    @GetMapping(value = {"/p/get/user/menu"})
    public R<List<UserMenuVO>> getUserMenus() {
        return R.successV2(userMenuRelationService.getMenuListByLoginUser());
    }

}
