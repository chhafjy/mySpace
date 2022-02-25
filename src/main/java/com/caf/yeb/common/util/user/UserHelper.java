package com.caf.yeb.common.util.user;

import com.caf.yeb.config.security.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author chenhaohao
 * @version 1.0
 */
@Slf4j
@Component
public class UserHelper {
    @Resource
    HttpServletRequest request;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    /**
     * 获取登录用户ID
     */
    public String getUserId() {
        String token = request.getHeader("Authorization");
        Claims claims = jwtTokenUtil.getClaimsByToken(token);
        log.info("token is {}", token);
        if (claims == null) return null;
        return (String) claims.get("userId");
    }

}
