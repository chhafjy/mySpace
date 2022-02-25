package com.caf.yeb.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2021/11/3 16:10
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(new MyTestInterceptor());
        //拦截所有请求
        interceptor.addPathPatterns("/**");
        //排除不拦截的请求
        List<String> excludes = new ArrayList();
        excludes.add("/login/captcha");
        excludes.add("/user/login");
        excludes.add("/p/register/login/user");
        excludes.add("/user/loginOut");
        interceptor.excludePathPatterns(excludes);
    }
}
