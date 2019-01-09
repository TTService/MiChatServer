package com.ttsource.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-09 13:48
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
public class UserNotFoundException extends UsernameNotFoundException {


    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
