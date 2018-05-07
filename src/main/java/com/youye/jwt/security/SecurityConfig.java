package com.youye.jwt.security;

import com.youye.jwt.AuthenticationFilter;
import com.youye.jwt.LoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 15:33
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
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf验证
        http.csrf().disable()
            // 对请求进行认证
            .authorizeRequests()
            // 所有 / 的所有请求 都放行
            .antMatchers("/").permitAll()
            // 所有 /login 的POST请求 都放行
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            // 添加权限检测
            .antMatchers("/hello").hasAuthority("AUTH_WRITE")
            // 角色检测
            .antMatchers("/world").hasRole("ADMIN")
            // 所有请求需要身份认证
            .anyRequest().authenticated()
            .and()
            // 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容
            .addFilterBefore(new LoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
            // 添加一个过滤器验证其他请求的Token是否合法
            .addFilterBefore(new AuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new UsernamePasswordAuthenticationProvider());
    }
}
