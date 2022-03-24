package com.caf.yeb.service.dynamic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caf.yeb.beans.dynamic.model.Dynamic;
import com.caf.yeb.beans.dynamic.request.AddDynamicParam;
import com.caf.yeb.beans.dynamic.request.AddOrRemoveFavoriteParam;
import com.caf.yeb.beans.dynamic.request.AllDynamicParam;
import com.caf.yeb.beans.dynamic.request.ThumbsUpParam;
import com.caf.yeb.beans.dynamic.response.AllDynamicVO;
import com.caf.yeb.beans.user.model.User;
import com.caf.yeb.common.pojo.PageVO;
import com.caf.yeb.common.util.bean.BeanUtils;
import com.caf.yeb.common.util.bean.IdUtils;
import com.caf.yeb.common.util.redis.RedisUtils;
import com.caf.yeb.common.util.user.UserHelper;
import com.caf.yeb.dao.dynamic.DynamicDao;
import com.caf.yeb.service.dynamic.DynamicService;
import com.caf.yeb.service.user.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * (Dynamic)表服务实现类
 *
 * @author chenhaohao
 */
@Slf4j
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicDao, Dynamic> implements DynamicService {

    @Resource
    UserHelper userHelper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    UserService userService;

    private static final String TAGS_KEY = "Tags";

    private static final String FAVORITE_KEY = "Favorite";

    private static final String EL_ICON_STAR_OFF = "el-icon-star-off";

    private static final String EL_ICON_STAR_ON = "el-icon-star-on";

    /**
     * 复写基类通过ID获取详情数据
     *
     * @param id 主键ID
     * @return 数据库对象数据
     */
    @Override
    public Dynamic getById(Serializable id) {
        return lambdaQuery().eq(Dynamic::getDynamicId, id).eq(Dynamic::getDeleteFlag, 0).last("limit 1").one();
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
        return lambdaUpdate().set(Dynamic::getDeleteFlag, 1)
                .in(Dynamic::getDynamicId, idsList).update();
    }

    /**
     * 新增、编辑状态
     *
     * @param param
     */
    @Override
    public void saveOrUpdateDynamic(AddDynamicParam param) {
        String dynamicId = param.getDynamicId();
        log.info("dynamicId is {}", dynamicId);
        Dynamic dynamic = new Dynamic();
        if(StringUtils.isBlank(dynamicId)){
            log.info("start save");
            dynamic.setDynamicId(IdUtils.getStrId());
            dynamic.setContent(param.getContent());
            dynamic.setUserId(userHelper.getUserId());
            this.save(dynamic);
        }else {
            log.info("start update");
            lambdaUpdate().set(Dynamic::getContent, param.getContent()).eq(Dynamic::getDynamicId, dynamicId).update();
        }
    }

    /**
     * 点赞
     *
     * @param param : 入参
     * @return {@link}
     * @author chenhaohao
     */
    @Override
    public void thumbsUp(ThumbsUpParam param) {

        String userId = userHelper.getUserId();
        String key = TAGS_KEY + param.getDynamicId();

        log.info("isThumbsUp is {}", param.getIsThumbsUp());

        //0-点赞  1-取消点赞
        if(param.getIsThumbsUp() == 0){
            redisUtils.addSet(key, userId);
        }

        if(param.getIsThumbsUp() == 1){
            redisUtils.removeSet(key, userId);
        }
    }

    /**
     * 获取全部动态
     *
     * @param param : 入参
     * @return {@link AllDynamicVO}
     * @author chenhaohao
     * @date 2022/3/15 22:56
     */
    @Override
    public AllDynamicVO getAllDynamic(AllDynamicParam param) {
        String userId = userHelper.getUserId();
        Page<Object> page = PageHelper.startPage(param.getPage().getPageNum(), param.getPage().getPageSize());
        List<Dynamic> list = lambdaQuery().eq(param.getIsMy() == 1, Dynamic::getUserId, userId).list();
        if(Collections.isEmpty(list)){
            return AllDynamicVO.builder().list(java.util.Collections.emptyList()).page(BeanUtils.toBean(page, PageVO.class)).build();
        }
        //获取用户信息
        Set<String> userIds = list.stream().map(Dynamic::getUserId).collect(Collectors.toSet());
        List<User> users = userService.getByIds(userIds);
        Map<String, User> userMap = new HashMap();
        if(!CollectionUtils.isEmpty(users)){
            userMap = users.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
        }
        List<AllDynamicVO.AllDynamic> vo = new ArrayList<>();
        //获取当前登录人的收藏动态
        Set<Object> set = redisUtils.getSet(userId + FAVORITE_KEY);
        for (Dynamic s : list) {
            AllDynamicVO.AllDynamic build = AllDynamicVO.AllDynamic.builder().dynamicId(s.getDynamicId())
                    .content(s.getContent()).viewCount(s.getViewCount()).isFavorites(0)
                    .favoriteIcon(EL_ICON_STAR_OFF).build();
            User user = userMap.get(s.getUserId());
            if(user != null){
                build.setUserName(user.getUserName());
                build.setAvatar(user.getAvatarFileNo());
            }
            if(!Collections.isEmpty(set)){
                build.setFavoriteIcon(set.contains(s.getDynamicId()) ? EL_ICON_STAR_ON : EL_ICON_STAR_OFF);
                build.setIsFavorites(set.contains(s.getDynamicId()) ? 1 : 0);
            }
            vo.add(build);
        }
        return AllDynamicVO.builder().list(vo).page(BeanUtils.toBean(page, PageVO.class)).build();
    }

    /**
     * 收藏或者取消收藏
     *
     * @param param : 入参
     * @return {@link}
     * @author chenhaohao
     * @date 2022/3/19 0:49
     */
    @Override
    public void addOrRemoveFavorite(AddOrRemoveFavoriteParam param) {
        String userId = userHelper.getUserId();
        Integer isFavorite = param.getIsFavorite();
        if(isFavorite == 0){
            redisUtils.addSet(userId + FAVORITE_KEY, param.getDynamicId());
        }else {
            redisUtils.removeSet(userId + FAVORITE_KEY, param.getDynamicId());
        }
    }
}
