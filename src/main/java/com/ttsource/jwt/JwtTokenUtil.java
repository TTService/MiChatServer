package com.ttsource.jwt;

import com.ttsource.redis.RedisUtil;
import com.ttsource.util.AESUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64UrlCodec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-15 13:50
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief:
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 过期时间(分钟)
     */
    private long expiration = 1000;

    private RedisUtil redisUtil;
    @Autowired
    JwtTokenUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());

        String token = generateToken(claims, userDetails.getUsername());
        redisUtil.cacheValue(userDetails.getUsername(), AESUtil.aesEncode(token), expiration);
        return token;
    }

    String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            String[] jwts = token.split("\\.");
            String claimsJsonStr = Base64UrlCodec.BASE64URL.decodeToString(jwts[1]);
            JSONObject jsonObject = new JSONObject(claimsJsonStr);
            String tempSecret = jsonObject.getString(CLAIM_KEY_USERNAME);
            claims = Jwts.parser()
                .setSigningKey(getSecret(tempSecret))
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateToken(Map<String, Object> claims, String tempSecret) {
        return Jwts.builder()
            .setClaims(claims)
            //.setExpiration(generateExpirationDate())
            .signWith(SignatureAlgorithm.HS512, getSecret(tempSecret))
            .compact();
    }

    Boolean validateToken(String token, UserDetails userDetails) {
        JwtUserInfo user = (JwtUserInfo) userDetails;

        // 该Token在数据库中不存在
        String storedToken = redisUtil.getValue(user.getUsername());
        storedToken = AESUtil.aesDecode(storedToken);
        if (StringUtils.isEmpty(storedToken)) {
            return false;
        }

        // 该用户在数据库存储的Token与上传的不一致
        if (!storedToken.equals(token)) {
            return false;
        }

        Claims claims = getClaimsFromToken(token);
        // 获取Token的用户信息
        final String username = claims.getSubject();
        // 获取Token的创建时间
        final Date created = new Date((Long) claims.get(CLAIM_KEY_CREATED));

        boolean isValidate = username.equals(user.getUsername())
            //&& !isTokenExpired(token)
            && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());

        if (isValidate) {
            // 更新有效时间
            redisUtil.cacheSet(username, AESUtil.aesEncode(token), expiration);
        }
        return isValidate;
    }

    private String getSecret(String tempSecret) {
        return AESUtil.aesEncode(tempSecret);
    }
}
