package com.caf.yeb.controller.user;

import com.caf.yeb.beans.user.request.LoginParam;
import com.caf.yeb.beans.user.request.RegisterUserParam;
import com.caf.yeb.beans.user.request.UpdateUserInfoParam;
import com.caf.yeb.beans.user.request.UserPageParam;
import com.caf.yeb.beans.user.response.LoginUserInfoVO;
import com.caf.yeb.beans.user.response.UserPageVO;
import com.caf.yeb.common.constact.CommonEnum;
import com.caf.yeb.common.constact.LoginEnum;
import com.caf.yeb.common.pojo.R;
import com.caf.yeb.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author chenhaohao
 * @version 1.0
 */
@RestController
public class LoginController {

    @Resource
    UserService userService;

    /**
     * 用户登录并返回token
     */
    @PostMapping(value = {"/user/login"})
    public R<String> login(HttpServletRequest request, @RequestBody LoginParam param) {
        String token = userService.login(param);
        if (token == null) {
            return R.error("账号或密码错误");
        }
        return R.success(LoginEnum.LOGIN_SUCCESS,token);
    }

    /**
     * 退出登录
     */
    @GetMapping(value = {"/user/loginOut"})
    public R loginOut() {
        return R.success(CommonEnum.IGNORE,"退出成功");
    }

    /**
     * 获取登录用户信息
     */
    @GetMapping(value = {"/login/user/info"})
    public R<LoginUserInfoVO> getLoginUserInfo() {
        return R.successV2(userService.getLoginUserInfo());
    }

    /**
     * 获取用户列表
     */
    @PostMapping(value = {"/p/user/page"})
    public R<UserPageVO> getUserPage(@RequestBody UserPageParam param) {
        return R.success(userService.getUserPage(param));
    }

    /**
     * 注册
     */
    @PostMapping(value = {"/p/register/login/user"})
    public R registerUser(@RequestBody RegisterUserParam param) {
        userService.registerUser(param);
        return R.success();
    }

    /**
     * 删除用户
     */
    @GetMapping(value = {"/p/user/delete"})
    public R userDelete(@RequestParam String userId) {
        userService.removeByIds(userId);
        return R.success();
    }

    /**
     * 设置管理员
     */
    @GetMapping(value = {"/p/set/user/admin"})
    public R setUserToAdmin(@RequestParam String userId) {
        userService.setUserToAdmin(userId);
        return R.success();
    }

    /**
     * 取消管理员
     */
    @GetMapping(value = {"/p/cancel/user/admin"})
    public R cancelUserToAdmin(@RequestParam String userId) {
        userService.cancelUserToAdmin(userId);
        return R.success();
    }

    /**
     * 更新用户信息
     */
    @PostMapping(value = {"/p/update/user/info"})
    public R updateUserInfo(@RequestBody @Valid UpdateUserInfoParam param) {
        userService.updateUserInfo(param);
        return R.success();
    }
}
