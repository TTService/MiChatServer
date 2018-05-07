package com.youye.controller;

import com.youye.jwt.util.JSONResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 17:26
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
@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        return "login success";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello() {
        return JSONResult.fillResultString(0, "", "hello");
    }

    @RequestMapping(value = "/world", method = RequestMethod.POST)
    public String world() {
        return JSONResult.fillResultString(0, "", "world");
    }

}
