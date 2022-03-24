package com.caf.yeb.controller.dynamic;

import com.caf.yeb.beans.dynamic.request.AddDynamicParam;
import com.caf.yeb.beans.dynamic.request.AddOrRemoveFavoriteParam;
import com.caf.yeb.beans.dynamic.request.AllDynamicParam;
import com.caf.yeb.beans.dynamic.request.ThumbsUpParam;
import com.caf.yeb.beans.dynamic.response.AllDynamicVO;
import com.caf.yeb.common.pojo.R;
import com.caf.yeb.service.dynamic.DynamicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenhaohao
 * @version 1.0
 */
@RestController
public class DynamicController {

    @Resource
    DynamicService dynamicService;

    /**
     * 新增或者编辑动态
     */
    @PostMapping(value = {"/p/add/or/update/dynamic"})
    public R addOrUpdateDynamic(@RequestBody @Valid AddDynamicParam param) {
        dynamicService.saveOrUpdateDynamic(param);
        return R.success();
    }

    /**
     * 删除动态
     */
    @GetMapping(value = {"/p/dynamic/delete"})
    public R dynamicDelete(@RequestParam String dynamicId) {
        dynamicService.removeByIds(dynamicId);
        return R.success();
    }

    /**
     * 点赞
     * @param param : 入参
     * @author chenhaohao
     * @return {@link}
     */
    @PostMapping(value = {"/p/dynamic/thumbs/up"})
    public R thumbsUp(@RequestBody @Valid ThumbsUpParam param) {
        dynamicService.thumbsUp(param);
        return R.success();
    }

    /**
     * 全部动态
     * @param param : 入参
     * @author chenhaohao
     * @date 2022/3/15 23:21
     * @return {@link com.caf.yeb.common.pojo.R<com.caf.yeb.beans.dynamic.response.AllDynamicVO>}
     */
    @PostMapping(value = {"/p/dynamic/all/page"})
    public R<AllDynamicVO> getAllDynamic(@RequestBody AllDynamicParam param) {
        return R.successV2(dynamicService.getAllDynamic(param));
    }

    /**
     * 收藏或者取消收藏
     * @param param : 入参
     * @author chenhaohao
     * @date 2022/3/19 0:49
     * @return {@link}
     */
    @PostMapping(value = {"/p/dynamic/favorite/add/remove"})
    public R addOrRemoveFavorite(@RequestBody AddOrRemoveFavoriteParam param) {
        dynamicService.addOrRemoveFavorite(param);
        return R.success();
    }

}
