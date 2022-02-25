package com.caf.yeb.config.security;

import com.caf.yeb.beans.user.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenhaohao
 * @version 1.0
 */
@Component
@Slf4j
@PropertySource("classpath:application.yml")
public class JwtTokenUtil {
    //用户ID
    public static final String CLAIM_KEY_USERID = "userId";
    //用户名
    public static final String CLAIM_KEY_USERNAME = "sub";
    //创建时间
    public static final String CLAIM_KEY_CREATED = "created";
    //加解密使用的秘钥（配置文件获取）
    @Value("${jwt.secret}")
    private String secret;
    //有效时间（配置文件获取）
    @Value("${expiration}")
    private long expiration;

    /**
     * 根据用户信息生成token
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERID, user.getUserId());
        claims.put(CLAIM_KEY_USERNAME, user.getUserName());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取用户名
     */
    public String getUserNameByToken(String token) {
        String userName;
        try {
            Claims claims = getClaimsByToken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }
        log.info("userName by token is {}", userName);
        return userName;
    }

    /**
     * 判断token是否有效
     */
    public Boolean validateToken(String token, User user) {
        String userName = getUserNameByToken(token);
        log.info("userName by token is {}", userName);
        if (StringUtils.isBlank(userName)) return false;
        return userName.equals(user.getUserName()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否可以被刷新
     */
    public Boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsByToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateByToken(token);
        log.info("token expiredDate is {}", expiredDate);
        return expiredDate.before(new Date());
    }

    /**
     * 获取token过期时间
     */
    private Date getExpiredDateByToken(String token) {
        Claims claims = getClaimsByToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取荷载
     */
    public Claims getClaimsByToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据荷载生成JWT token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成失效时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
