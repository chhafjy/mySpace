package com.caf.yeb.common.interceptor;

import com.caf.yeb.common.constact.LoginEnum;
import com.caf.yeb.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenhaohao
 * @version 1.0
 * @date 2021/11/3 15:56
 */
@Slf4j
public class MyTestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        String token = request.getHeader("Authorization");
        log.info("token is {}", token);
        if (StringUtils.isBlank(token)) {
            log.error("请求头中无法获取token");
            throw new BusinessException(LoginEnum.TOKEN_ERROR);
        }
        Map<String, String> allParams = getAllParams(request);
        String servletPath = request.getServletPath();
        log.info("请求路径:" + servletPath + "  请求参数为 param：" + allParams.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//        Object res = request.getAttribute("response");
//        Class<?> aClass = method.getDeclaringClass();
//        String name = method.getName();
//        log.info("方法名为 {}",name);
//        String jsonRes = null;
//        if(res != null){
//            jsonRes = JSON.toJSONString(res);
//        }
//        log.info("返回数据是 {}",jsonRes);
    }

    public Map<String, String> getAllParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<?> parameterNames = request.getParameterNames();
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String key = (String) parameterNames.nextElement();
                String value = request.getParameter(key);
                map.put(key, value);
            }
        }
        return map;
    }
}
