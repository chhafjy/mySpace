package com.caf.yeb.controller.dynamic;

import com.caf.yeb.beans.dynamic.request.AddDynamicParam;
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

}
