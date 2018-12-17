/*
package com.ttsource.jwt.annotation;

import com.ttsource.jwt.token.TokenManager;
import com.ttsource.jwt.token.TokenModel;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

*/
/**
 * **********************************************
 * <p/>
 * Date: 2018-05-29 23:50
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 用户注解拦截器。拦截带有<code>@User</code>注解的方法。
 * <p/>
 * history:
 * <p/>
 * **********************************************
 *//*

@Component
public class UserInterceptor implements HandlerInterceptor {

    private TokenManager tokenManager;

    @Autowired
    public UserInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到该方法，则直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果方法没有添加@Authorization的注解，则直接通过
        if (method.getAnnotation(User.class) == null) {
            return true;
        }

        System.out.println("[UserInterceptor]： preHandle");

        //从header中取出加密的token
        String authorization = request.getHeader(TokenManager.TOKEN);
        //从加密数据中获取token
        TokenModel model = tokenManager.getToken(authorization);
        if (tokenManager.checkToken(model)) {
            // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute("username", model.getUsername());
            return true;
        }
        //如果验证token失败，并且方法注明了Authorization，则返回用户未登录的错误
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
*/
