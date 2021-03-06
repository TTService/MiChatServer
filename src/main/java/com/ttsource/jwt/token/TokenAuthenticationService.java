package com.ttsource.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 15:36
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
public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 432_000_000;     // 5天
//    static final long EXPIRATIONTIME = 60_000;     // 1分钟
    static final String SECRET = "P@ssw02d";            // JWT密码
    static final String TOKEN_PREFIX = "Bearer";        // Token前缀
    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key

    public static void addAuthentication(HttpServletResponse response, String username) {
        // 生成JWT
        String JWT = Jwts.builder()
            // 保存权限（角色）
            .claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
            // 用户名写入标题
            .setSubject(username)
            // 有效期设置
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
            // 签名设置
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        // 将 JWT 写入 body
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("token", JWT);
            //response.getOutputStream().println(JSONResult.fillResultString(0, "", JWT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            Claims claims;
            try {
                // 解析 Token
                claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET)
                    // 去掉 Bearer
                    //.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .parseClaimsJws(token)
                    .getBody();
            } catch (ExpiredJwtException e) {
                claims = null;
            }

            if (claims == null)
                return null;

            // 拿用户名
            String user = claims.getSubject();

            // 得到 权限（角色）
            List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList((String) claims.get("authorities"));

            // 返回验证令牌
            return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) : null;
        }
        return null;
    }
}
