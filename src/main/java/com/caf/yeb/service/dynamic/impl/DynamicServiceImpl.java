package com.caf.yeb.service.dynamic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caf.yeb.beans.dynamic.model.Dynamic;
import com.caf.yeb.beans.dynamic.request.AddDynamicParam;
import com.caf.yeb.common.util.bean.IdUtils;
import com.caf.yeb.common.util.user.UserHelper;
import com.caf.yeb.dao.dynamic.DynamicDao;
import com.caf.yeb.service.dynamic.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public void thumbsUp() {

    }
}
