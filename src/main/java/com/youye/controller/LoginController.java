package com.youye.controller;

import com.youye.constant.ResultConstant;
import com.youye.controller.presenter.LoginPresenter;
import com.youye.controller.presenter.LoginPresenter.ILoginPresenter;
import com.youye.model.User;
import com.youye.model.result.ResultInfo;
import com.youye.service.UserService;
import com.youye.util.ErrCode;
import com.youye.util.JSONResult;
import com.youye.util.StringUtil;
import com.youye.util.VerificationCodeUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/user")
public class LoginController implements ILoginPresenter {

    @Autowired
    private UserService userService;

    private LoginPresenter mPresenter;

    public LoginController() {
        mPresenter = new LoginPresenter(this);
    }

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

    /**
     * 注册流程：
     * 1、用户首先输入手机号码，用来获取验证码。
     * 2、服务端检测手机号码是否合法，是否已被注册，如果都没有则下发注册验证码，并保存下发的验证码；否则提醒用户相关错误。
     * 3、客户端输入验证码，并提交。
     * 4、服务端校验验证码是否与手机号码一致，并检验验证码的有效期。如果一致且在有效期内则通过；否则提醒用户相关错误。
     * 5、客户端完善注册信息，用户昵称、密码、性别、年龄、图像、地址、邮箱、描述等等。
     * 6、服务端保存用户信息。
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultInfo reigster(@ModelAttribute User user) {
        if (user == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "接口调用错误，请包装用户信息");

        ResultInfo resultInfo = mPresenter.isValidForAddUser(user);
        if (resultInfo != null)
            return resultInfo;

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        user.setInsertTime(date);
        user.setUpdateTime(date);
        return null;
    }

    /**
     * 获取手机短信验证码
     */
    @RequestMapping(value = "/verification/{mobile}")
    public ResultInfo  getPhoneVerificationCode(@PathVariable String mobile) {
        if (!StringUtil.isMobileNo(mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "手机号码格式不正确");
        }
        //TODO 判断Redis中是否有该号码相对于的注册验证码，如果有则拒绝生成，提醒用户提交频繁，或等稍后再试

        // 生成验证码
        String verificationCode = VerificationCodeUtil.generateIntVerificationCode(6);

        //TODO 将验证码与手机号码关联存于Redis 中
        //TODO 调用 发送短信接口，发送短信

        Map<String, String> result = new HashMap<>();
        result.put("mobile", mobile);
        result.put("code", verificationCode);
        return new ResultInfo(ErrCode.OK, result, "获取验证码成功");
    }

    /**
     * 校验注册时的手机短信验证码
     */
    @RequestMapping(value = "/verification/check")
    public ResultInfo  checkPhoneVerificationCode(@RequestParam String mobile, @RequestParam String code) {
        if (!StringUtil.isMobileNo(mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "手机号码格式不正确");
        }

        if (StringUtil.isEmpty(code) || code.length() != 6) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "手机验证码不正确");
        }

        //TODO 根据手机号从Redis中获取验证码，并比较参数中的验证码，相同则成功，否则失败。
        //TODO 成功则删除Redis中对应号码的记录。 是否需要？？？
        //TODO 将手机号码进行加密返回

        return new ResultInfo(ErrCode.OK, mobile, "注册码校验成功");
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
