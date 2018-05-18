package com.youye.controller;

import com.youye.model.User;
import com.youye.model.result.ResultInfo;
import com.youye.service.UserService;
import com.youye.util.ErrCode;
import com.youye.util.JSONResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getAttribute("username");
        String token = (String) request.getAttribute("token");
        if (username == null || token == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户验证错误");

        User user = userService.findOneByUsername(username);
        response.setHeader("token", token);

        return new ResultInfo(ErrCode.OK, user, "login success");
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
