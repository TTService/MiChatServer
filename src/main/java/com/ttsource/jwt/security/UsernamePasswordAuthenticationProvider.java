package com.ttsource.jwt.security;

import com.ttsource.exception.UserNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 15:38
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 用户账号验证模块
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) {

        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailService.loadUserByUsername(name);
        if (userDetails == null) {
            throw new UserNotFoundException("用户不存在");
        }

        if (!userDetails.getPassword().equals(password))
            throw new BadCredentialsException("用户名或密码错误");

        return new UsernamePasswordAuthenticationToken(name, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    void setUserDetailService(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }
}
