package com.youye.jwt;

import com.youye.jwt.token.TokenAuthenticationService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 16:03
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
public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = TokenAuthenticationService
            .getAuthentication((HttpServletRequest)servletRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
