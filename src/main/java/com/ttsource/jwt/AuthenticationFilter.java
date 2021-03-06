package com.ttsource.jwt;

import com.ttsource.common.Global.Param;
import com.ttsource.jwt.token.TokenManager;
import com.ttsource.jwt.token.TokenModel;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 16:03
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 接口拦截器，判断用户是否有权限获取资源
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class AuthenticationFilter extends GenericFilterBean {

    private TokenManager tokenManager;

    public AuthenticationFilter(TokenManager tokenManager) {
        super();
        this.tokenManager = tokenManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)servletRequest);
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authentication = request.getHeader(TokenManager.TOKEN);
        TokenModel tokenModel = tokenManager.getToken(authentication);
        boolean isChecked = tokenManager.checkToken(tokenModel);
        if (isChecked) {
            request.setAttribute(Param.IDENTIFIER, tokenModel.getIdentifier());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenModel.getIdentifier(), null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

}
