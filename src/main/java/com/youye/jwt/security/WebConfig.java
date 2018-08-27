package com.youye.jwt.security;

import com.youye.jwt.resolver.UserInfoResolver;
import com.youye.service.UserInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * **********************************************
 * <p/>
 * Date: 2018-06-01 21:59
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
public class WebConfig extends WebMvcConfigurationSupport {

    private UserInfoService userInfoService;

    @Autowired
    public WebConfig(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserInfoResolver(userInfoService));
    }
}
