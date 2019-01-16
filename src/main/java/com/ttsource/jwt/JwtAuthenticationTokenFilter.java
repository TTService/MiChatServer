package com.ttsource.jwt;

import com.ttsource.util.StringUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-15 13:19
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
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Value("${environment.jwt.header}")
    private String tokenHeader;

    @Value("${environment.jwt.head}")
    private String tokenHead;

    @Autowired
    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
        @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain)
        throws ServletException, IOException {

        authentication(httpServletRequest);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authentication(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);

        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(tokenHead)) {
            return;
        }

        final String authToken = authHeader.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        logger.info("checking authentication " + username);

        if (StringUtil.isEmpty(username) || SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        // 这里是否有好的做法，避免对数据库进行查询
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            logger.info("authenticated user " + username + ", setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
