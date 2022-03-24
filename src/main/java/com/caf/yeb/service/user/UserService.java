package com.caf.yeb.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.caf.yeb.beans.user.model.User;
import com.caf.yeb.beans.user.request.LoginParam;
import com.caf.yeb.beans.user.request.RegisterUserParam;
import com.caf.yeb.beans.user.request.UpdateUserInfoParam;
import com.caf.yeb.beans.user.request.UserPageParam;
import com.caf.yeb.beans.user.response.LoginUserInfoVO;
import com.caf.yeb.beans.user.response.UserPageDTO;
import com.caf.yeb.beans.user.response.UserPageVO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * (User)表服务接口
 *
 * @author chenhaohao
 */
public interface UserService extends IService<User> {

    /**
     * 根据ID获取基本信息
     *
     * @param id 主键ID
     * @return {@link }
     * @author chenhaohao
     */
    @Override
    User getById(Serializable id);

    /**
     * 根据IDs获取基本信息
     *
     * @param ids 主键ID
     * @return {@link }
     * @author chenhaohao
     */
    List<User> getByIds(Set<String> ids);

    /**
     * 逻辑删除数据
     *
     * @param ids 主键集合
     * @return {@link boolean}
     * @author chenhaohao
     */
    boolean removeByIds(String... ids);

    /**
     * 根据账号/手机号和密码获取用户信息
     */
    User isLoginByAccount(String account, String password);


    /**
     * 登录并返回token
     */
    String login(LoginParam param);

    /**
     * 获取登录用户信息
     */
    LoginUserInfoVO getLoginUserInfo();

    /**
     * 获取用户列表
     */
    UserPageVO getUserPage(UserPageParam param);

    /**
     * 注册登录账号
     */
    void registerUser(RegisterUserParam param);

    /**
     * 重置密码
     */
    void resetPassword(String userId);

    /**
     * 设置为管理员
     */
    void setUserToAdmin(String userId);

    /**
     * 取消管理员
     */
    void cancelUserToAdmin(String userId);

    /**
     * 更新用户信息
     */
    void updateUserInfo(UpdateUserInfoParam param);
}
