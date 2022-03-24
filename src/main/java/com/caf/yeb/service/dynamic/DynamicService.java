package com.caf.yeb.service.dynamic;


import com.baomidou.mybatisplus.extension.service.IService;
import com.caf.yeb.beans.dynamic.model.Dynamic;
import com.caf.yeb.beans.dynamic.request.AddDynamicParam;
import com.caf.yeb.beans.dynamic.request.AddOrRemoveFavoriteParam;
import com.caf.yeb.beans.dynamic.request.AllDynamicParam;
import com.caf.yeb.beans.dynamic.request.ThumbsUpParam;
import com.caf.yeb.beans.dynamic.response.AllDynamicVO;

import java.io.Serializable;


/**
 * (Dynamic)表服务接口
 *
 * @author chenhaohao
 */
public interface DynamicService extends IService<Dynamic> {

    /**
     * 根据ID获取基本信息
     *
     * @param id 主键ID
     * @return {@link}
     * @author chenhaohao
     */
    @Override
    public Dynamic getById(Serializable id);

    /**
     * 逻辑删除数据
     *
     * @param ids 主键集合
     * @return {@link boolean}
     * @author chenhaohao
     */
    public boolean removeByIds(String... ids);

    /**
     * 新增、编辑状态
     *  * @param param : 入参
     * @author chenhaohao
     * @date 2022/2/22 17:16
     * @return {@link}
     */
    void saveOrUpdateDynamic(AddDynamicParam param);


    /**
     * 点赞
     * @param param : 入参
     * @author chenhaohao
     * @return {@link}
     */
    void thumbsUp(ThumbsUpParam param);

    /**
     * 获取全部动态
     * @param param : 入参
     * @author chenhaohao
     * @date 2022/3/15 22:56
     * @return {@link com.caf.yeb.beans.dynamic.response.AllDynamicVO}
     */
    AllDynamicVO getAllDynamic(AllDynamicParam param);

    /**
     * 收藏或者取消收藏
     * @param param : 入参
     * @author chenhaohao
     * @date 2022/3/19 0:49
     * @return {@link}
     */
    void addOrRemoveFavorite(AddOrRemoveFavoriteParam param);

}
