package com.ttsource.jwt.security;

import com.ttsource.jwt.AuthenticationFilter;
import com.ttsource.jwt.JwtAuthenticationTokenFilter;
import com.ttsource.jwt.JwtTokenUtil;
import com.ttsource.jwt.LoginFilter;
import com.ttsource.jwt.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 15:33
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: Spring Security 的配置文件
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private TokenManager tokenManager;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SecurityConfig(
        UserDetailsService userDetailsService,
        TokenManager tokenManager,
        JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.tokenManager = tokenManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .userDetailsService(this.userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter(userDetailsService, jwtTokenUtil);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        UsernamePasswordAuthenticationProvider authenticationProvider = new UsernamePasswordAuthenticationProvider();
        authenticationProvider.setUserDetailService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 由于这里使用jwt, 所部不需要csrf, 关闭csrf验证
            .csrf().disable()
            // 基于Token， 所有不需要Session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // 对请求进行认证
            .authorizeRequests()
            // 所有 / 的所有请求 都放行
            .antMatchers(
                HttpMethod.GET,
                "/",
                "/.html",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
            ).permitAll()
            // 所有 / 的所有请求 都放行
            .antMatchers("/").permitAll()
            .antMatchers("/banner/**").permitAll()
            .antMatchers("/verification/**").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/user/recommend").permitAll()
            .antMatchers("/user/new").permitAll()
            .antMatchers("/user/active").permitAll()
            .antMatchers("/oss/**").permitAll()
            // 所有 /login 的POST请求 都放行
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            // 添加权限检测
            //.antMatchers("/hello").hasAuthority("AUTH_WRITE")
            // 角色检测
            //.antMatchers("/world").hasRole("ADMIN")
            // 所有请求需要身份认证
            .anyRequest().authenticated()
            .and()
            // 添加一个过滤器 所有访问 /login 的请求交给 LoginFilter 来处理 这个类处理所有的JWT相关内容
            //.addFilterBefore(new LoginFilter("/login", authenticationManager(), tokenManager), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
            /*// 添加一个过滤器验证其他请求的Token是否合法
            .addFilterBefore(new AuthenticationFilter(tokenManager), BasicAuthenticationFilter.class)*/;

            // 禁用缓存
            http.headers().cacheControl();
    }
}
