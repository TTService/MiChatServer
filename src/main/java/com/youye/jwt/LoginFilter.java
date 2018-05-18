package com.youye.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youye.jwt.token.TokenAuthenticationService;
import com.youye.jwt.token.TokenManager;
import com.youye.jwt.token.TokenModel;
import com.youye.util.ErrCode;
import com.youye.util.JSONResult;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 15:41
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
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenManager tokenManager;

    public LoginFilter(String url, AuthenticationManager authenticationManager, TokenManager tokenManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
        this.tokenManager = tokenManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException, IOException {
        // JSON反序列化成 AccountCredentials
        AccountCredential cred = new ObjectMapper().readValue(request.getInputStream(), AccountCredential.class);

        // 返回一个验证令牌
        return getAuthenticationManager()
            .authenticate(new UsernamePasswordAuthenticationToken(cred.getUsername(), cred.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
        throws IOException, ServletException {
        //TokenAuthenticationService.addAuthentication(res, auth.getName());
        TokenModel tokenModel = tokenManager.createToken(auth.getName());
        req.setAttribute("username", tokenModel.getUsername());
        req.setAttribute("token", tokenModel.getToken());
        chain.doFilter(req, res);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().println(JSONResult
            .fillResultString(ErrCode.BAD_REQUEST, failed.getMessage(), ""));
    }
}
