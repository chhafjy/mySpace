package com.caf.yeb.service.user.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caf.yeb.beans.user.constact.UserStatusEnum;
import com.caf.yeb.beans.user.model.User;
import com.caf.yeb.beans.user.request.LoginParam;
import com.caf.yeb.beans.user.request.RegisterUserParam;
import com.caf.yeb.beans.user.request.UpdateUserInfoParam;
import com.caf.yeb.beans.user.request.UserPageParam;
import com.caf.yeb.beans.user.response.LoginUserInfoVO;
import com.caf.yeb.beans.user.response.UserPageDTO;
import com.caf.yeb.beans.user.response.UserPageVO;
import com.caf.yeb.common.constact.CommonEnum;
import com.caf.yeb.common.exception.BusinessException;
import com.caf.yeb.common.pojo.PageVO;
import com.caf.yeb.common.pojo.R;
import com.caf.yeb.common.util.bean.BeanUtils;
import com.caf.yeb.common.util.bean.IdUtils;
import com.caf.yeb.common.util.redis.RedisUtils;
import com.caf.yeb.common.util.user.AvatarHelper;
import com.caf.yeb.common.util.user.UserHelper;
import com.caf.yeb.config.security.JwtTokenUtil;
import com.caf.yeb.dao.user.UserDao;
import com.caf.yeb.service.user.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
 * (User)表服务实现类
 *
 * @author chenhaohao
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Resource
    UserHelper userHelper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    AvatarHelper avatarHelper;

    private static final String DEFAULT_PASSWORD = "123456";

    private static final String STRING_NULL = "null";

    /**
     * 复写基类通过ID获取详情数据
     *
     * @param id 主键ID
     * @return 数据库对象数据
     */
    @Override
    public User getById(Serializable id) {
        return lambdaQuery().eq(User::getUserId, id).eq(User::getDeleteFlag, 0).last("limit 1").one();
    }

    /**
     * 根据IDs获取基本信息
     *
     * @param ids 主键ID
     * @return {@link }
     * @author chenhaohao
     */
    @Override
    public List<User> getByIds(Set<String> ids) {
        return lambdaQuery().in(User::getUserId, ids).eq(User::getDeleteFlag, 0).list();
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
        return lambdaUpdate().set(User::getDeleteFlag, 1)
                .in(User::getUserId, idsList).update();
    }

    /**
     * 根据账号/手机号和密码获取用户信息
     *
     * @param account
     * @param password
     */
    @Override
    public User isLoginByAccount(String account, String password) {
        Assert.isTrue(!StringUtils.isBlank(account),CommonEnum.PARAM_ERROR.getMessage());
        Assert.isTrue(!StringUtils.isBlank(password),CommonEnum.PARAM_ERROR.getMessage());
        return lambdaQuery().eq(StringUtils.isNotBlank(account), User::getAccount, account)
                .or().eq(StringUtils.isNotBlank(account), User::getPhone, account)
                .eq(User::getPassword, password)
                .eq(User::getDeleteFlag,0)
                .eq(User::getStatus, UserStatusEnum.NORMAL.getValue())
                .last("limit 1").one();
    }

    /**
     * 登录并返回token
     *
     * @param param
     */
    @Override
    public String login(LoginParam param) {
        String loginValidateCode = null;
        try {
            loginValidateCode = String.valueOf(redisUtils.get(param.getCaptcha()));
        } catch (Exception e) {
            throw new BusinessException(CommonEnum.FAIL, "验证码校验失败，请刷新重试");
        }
        if (loginValidateCode == null || STRING_NULL.equals(loginValidateCode)) {
            //验证码错误
            throw new BusinessException(CommonEnum.FAIL, "验证码错误，请刷新重试");
        }
        //验证用户
        User user = this.isLoginByAccount(param.getAccount(), param.getPassword());
        if(user == null) {
            return null;
        }
        //生成token
        return jwtTokenUtil.generateToken(user);
    }

    /**
     * 获取登录用户信息
     */
    @Override
    public LoginUserInfoVO getLoginUserInfo() {
        String userId = userHelper.getUserId();
        if(StringUtils.isBlank(userId)){
            log.info("userId is null");
            return null;
        }
        User user = this.getById(userId);
        LoginUserInfoVO userInfo = BeanUtils.toBean(user, LoginUserInfoVO.class);
        userInfo.setAvatar(user.getAvatarFileNo());
        return userInfo;
    }

    /**
     * 获取用户列表
     */
    @Override
    public UserPageVO getUserPage(UserPageParam param) {
        Page page = PageHelper.startPage(param.getPage().getPageNum(), param.getPage().getPageSize());
        List<User> list = lambdaQuery().eq(StringUtils.isNotBlank(param.getUserName()), User::getUserName, param.getUserName())
                .eq(User::getDeleteFlag, 0)
                .list();
        if(CollectionUtils.isEmpty(list)){
            log.info("user list is empty");
            return UserPageVO.builder().list(Collections.emptyList()).page(new PageVO(page)).build();
        }
        List<UserPageDTO> listVO = new ArrayList<>();
        list.forEach(s -> {
            UserPageDTO build = UserPageDTO.builder().userId(s.getUserId()).userName(s.getUserName()).icon(s.getAvatarFileNo())
                    .account(s.getAccount()).phone(s.getPhone()).build();
            listVO.add(build);

        });
        return UserPageVO.builder().list(listVO).page(new PageVO(page)).build();
    }

    /**
     * 注册登录账号
     *
     * @param param
     */
    @Override
    public void registerUser(RegisterUserParam param) {
        User one = this.lambdaQuery().eq(User::getPhone, param.getPhone()).eq(User::getDeleteFlag, 0)
                .last("limit 1").one();
        if(one != null){
            log.info("phone is {}", param.getPhone());
            throw new BusinessException(CommonEnum.FAIL, "用户已存在");
        }
        User user = BeanUtils.toBean(param, User.class);
        user.setUserId(IdUtils.getStrId());
        user.setUserUuid(IdUtils.getStrId());
        user.setAccount(param.getPhone());
        user.setType(2);
        user.setAvatarFileNo(avatarHelper.getDefaultAvatar());
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        this.save(user);
    }

    /**
     * 重置密码
     *
     * @param userId
     */
    @Override
    public void resetPassword(String userId) {
        log.info("用户ID是 {}", userId);
        if (StringUtils.isBlank(userId)) {
            return;
        }
        lambdaUpdate().set(User::getPassword, DEFAULT_PASSWORD).eq(User::getUserId, userId).update();
    }

    /**
     * 设置为管理员
     *
     * @param userId
     */
    @Override
    public void setUserToAdmin(String userId) {
        log.info("用户ID是 {}", userId);
        if (StringUtils.isBlank(userId)) {
            return;
        }
        lambdaUpdate().set(User::getType, 1).eq(User::getUserId, userId).update();
    }

    /**
     * 取消管理员
     *
     * @param userId
     */
    @Override
    public void cancelUserToAdmin(String userId) {
        log.info("用户ID是 {}", userId);
        if (StringUtils.isBlank(userId)) {
            return;
        }
        lambdaUpdate().set(User::getType, 2).eq(User::getUserId, userId).update();
    }

    /**
     * 更新用户信息
     *
     * @param param
     */
    @Override
    public void updateUserInfo(UpdateUserInfoParam param) {
        String userId = param.getUserId();
        log.info("userId is {}", userId);
        if(StringUtils.isBlank(userId)){
            throw new BusinessException(CommonEnum.FAIL, "用户ID不能为空");
        }
        //获取用户信息
        User user = this.getById(userId);
        if(user == null){
            log.info("user info is null by userId {}", userId);
            throw new BusinessException(CommonEnum.FAIL, "用户信息为空");
        }
        User one = lambdaQuery().ne(User::getUserId, userId).eq(User::getPhone, param.getPhone())
                .eq(User::getDeleteFlag, 0).last("limit 1").one();
        if(one != null){
            throw new BusinessException(CommonEnum.FAIL, "手机号{" + param.getPhone() + "}已经存在");
        }
        User accountOne = lambdaQuery().ne(User::getUserId, userId).eq(User::getAccount, param.getAccount())
                .eq(User::getDeleteFlag, 0).last("limit 1").one();
        if(accountOne != null){
            throw new BusinessException(CommonEnum.FAIL, "账号{" + param.getAccount() + "}已经存在");
        }
        boolean b = user.getAccount().equals(param.getAccount());
        lambdaUpdate().set(StringUtils.isNotBlank(param.getUserName()), User::getUserName, param.getUserName())
                .set(StringUtils.isNotBlank(param.getPhone()), User::getPhone, param.getPhone())
                .set(StringUtils.isNotBlank(param.getAccount()), User::getAccount, param.getAccount())
                .set(!b,User::getModifyAccount,1)
                .eq(User::getUserId, userId).update();
    }
}
