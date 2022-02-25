package com.caf.yeb.controller.captcha;

import com.caf.yeb.common.pojo.R;
import com.caf.yeb.common.util.captcha.CaptchaUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenhaohao
 * @version 1.0
 */
@Slf4j
@RestController
public class CaptchaController {
    @Resource
    private DefaultKaptcha captchaProducer;

    @Resource
    private CaptchaUtil captchaUtil;
    /**
     * 登录验证码SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "login_validate_code";

    /**
     * 登录验证码图片
     */
    @GetMapping(value = {"/login/captcha"})
    public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            //输出验证码图片方法
            captchaUtil.validateCode(request, response, captchaProducer, LOGIN_VALIDATE_CODE);
        } catch (Exception e) {
            log.error("获取验证码失败>>>>   ", e);
        }
    }

    /**
     * 检查验证码是否正确
     */
    @GetMapping("/check/login/captcha")
    public R checkLoginValidateCode(HttpServletRequest request, @RequestParam("captcha") String validateCode) {
        String loginValidateCode = request.getSession().getAttribute(LOGIN_VALIDATE_CODE).toString();
        if (loginValidateCode == null) {
            return R.success("验证码过期");//验证码过期
        } else if (loginValidateCode.equals(validateCode)) {
            return R.success("验证码正确");//验证码正确
        } else if (!loginValidateCode.equals(validateCode)) {
            return R.error("验证码错误");//验证码不正确
        }
        return R.success();
    }
}
