package com.ttsource.jwt.resolver;

import com.ttsource.common.Global.Param;
import com.ttsource.jwt.annotation.User;
import com.ttsource.model.user.UserInfoDTO;
import com.ttsource.service.UserInfoService;
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
 * brief: <code>User注解拦截器</code>, 配有该注解的标签在本类中生成{@link UserInfoDTO}对象
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

    /**
     *如果参数类型是UserInfo,并且含有{@link com.ttsource.jwt.annotation.User}的注解则支持转换
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(UserInfoDTO.class)
            && methodParameter.hasParameterAnnotation(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
        WebDataBinderFactory webDataBinderFactory) {
        // 取出鉴权时存入的用户信息
        String username;
        try {
            username = (String) nativeWebRequest.getAttribute(Param.IDENTIFIER, RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e) {
            username = null;
        }

        if (username != null) {
            return userInfoService.findUserInfoByIdentifier(username);
        }
        return new MissingServletRequestPartException(Param.IDENTIFIER);
    }
}
