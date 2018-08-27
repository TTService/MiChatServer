package com.youye.jwt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * **********************************************
 * <p/>
 * Date: 2017-03-13 14:16
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface User {

}
