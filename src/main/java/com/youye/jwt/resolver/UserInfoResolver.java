package com.youye.jwt.resolver;

import com.youye.common.Global.Param;
import com.youye.jwt.annotation.User;
import com.youye.model.UserInfo;
import com.youye.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * **********************************************
 * <p/>
 * Date: 2018-06-01 08:45
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: <code>User注解拦截器</code>, 配有该注解的标签在本类中生成{@link com.youye.model.UserInfo}对象
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
@Component
public class UserInfoResolver implements HandlerMethodArgumentResolver {


    private UserInfoService userInfoService;

    public UserInfoResolver(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是UserInfo,并且含有<code>@User</code>的注解则支持转换
        return methodParameter.getParameterType().isAssignableFrom(UserInfo.class)
            && methodParameter.hasParameterAnnotation(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
        WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 取出鉴权时存入的用户信息
        String username;
        try {
            username = (String) nativeWebRequest.getAttribute(Param.USERNAME, RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e) {
            username = null;
        }

        if (username != null) {
            return userInfoService.findOneByUsername(username);
        }
        return new MissingServletRequestPartException(Param.USERNAME);
    }
}
