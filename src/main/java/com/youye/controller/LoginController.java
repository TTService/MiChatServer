package com.youye.controller;

import com.youye.controller.presenter.LoginPresenter;
import com.youye.controller.presenter.LoginPresenter.ILoginPresenter;
import com.youye.jwt.token.TokenManager;
import com.youye.jwt.token.TokenModel;
import com.youye.model.user.RegisterVO;
import com.youye.model.user.UserAuthDO;
import com.youye.model.user.UserInfoDTO;
import com.youye.model.result.ResultInfo;
import com.youye.redis.RedisUtil;
import com.youye.remote.JavaClassExecuter;
import com.youye.service.UserInfoService;
import com.youye.util.ErrCode;
import com.youye.util.StringUtil;
import com.youye.util.VerificationCodeUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
public class LoginController implements ILoginPresenter {
    private Logger mLogger = LoggerFactory.getLogger(LoginController.class);

    private UserInfoService userInfoService;
    private RedisUtil redisUtil;
    private TokenManager tokenManager;

    private LoginPresenter mPresenter;

    @Autowired
    public LoginController(UserInfoService userInfoService, RedisUtil redisUtil, TokenManager tokenManager) {
        this.userInfoService = userInfoService;
        this.redisUtil = redisUtil;
        this.tokenManager = tokenManager;
        mPresenter = new LoginPresenter(this, userInfoService);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response) {
        String identifier = (String) request.getAttribute("identifier");
        String token = (String) request.getAttribute("token");
        if (identifier == null || token == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户验证错误");

        UserInfoDTO user = userInfoService.findUserInfoByIdentifier(identifier);
        user.setLogged(1);
        userInfoService.updateUserLoginState(user.getUserId(), user.getLogged());
        response.setHeader("token", token);

        return new ResultInfo(ErrCode.OK, user, "login success");
    }

    /**
     * 注册流程：
     * 1、用户首先输入手机号码，用来获取验证码。
     * 2、服务端检测手机号码是否合法，是否已被注册，如果都没有则下发注册验证码，并保存下发的验证码；否则提醒用户相关错误。
     * 3、客户端输入验证码，并提交。
     * 4、服务端校验验证码是否与手机号码一致，并检验验证码的有效期。如果一致且在有效期内则通过；否则提醒用户相关错误。
     * 5、客户端完善注册信息，用户昵称、密码等等。
     * 6、服务端保存用户信息。
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Transactional
    public ResultInfo register(@RequestBody RegisterVO registerVO, HttpServletRequest request) {
        try {
            if (registerVO == null)
                return new ResultInfo(ErrCode.BAD_REQUEST, "", "接口调用错误，请包装用户信息");

            ResultInfo resultInfo = mPresenter.isValidForAddUser(registerVO);
            if (resultInfo != null)
                return resultInfo;

            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            registerVO.setGmtCreate(date);
            registerVO.setGmtModified(date);

            boolean isSuccess = userInfoService.createUser(registerVO);
            if (!isSuccess) {
                throw new RuntimeException("数据库存储错误");
            }
            //userInfoService.addUser(user);

            if (registerVO.getUserId() <= 0)
                throw new RuntimeException("创建用户失败");


            TokenModel tokenModel = tokenManager.createToken(registerVO.getIdentify());
            //response.setHeader("token", tokenModel.getToken());

            request.setAttribute("token", tokenModel.getToken());
            return new ResultInfo(ErrCode.OK, registerVO, "注册成功");
        } catch (Exception e) {
            mLogger.error(e.getMessage());
            return new ResultInfo(ErrCode.INTERNAL_SERVER_ERROR, "", "注册失败");
        }
    }

    /**
     * 获取手机短信验证码。
     * 1. 判断该手机号码在数据库中是否被使用，如果有则不允许再用于用户注册
     *
     * 如何防止短信防刷：
     * 1、时间间隔：60秒内只能发送一次
     * 2、手机号限制：同一手机号，24小时内不能超过5条
     * 3、图片验证码机制：验证通过才发送验证码
     */
    @RequestMapping(value = "/verification/{mobile}")
    public ResultInfo  getPhoneVerificationCode(@PathVariable String mobile) {
        if (!StringUtil.isMobileNo(mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, null, "手机号码格式不正确");
        }

        UserAuthDO userInfo = userInfoService.getAuthByIdentifier(mobile);
        if (userInfo != null) {
            return new ResultInfo(ErrCode.BAD_REQUEST, null, "该号码已被注册");
        }

        //TODO 判断一分钟内是否生成过该号码对应的验证码，如果有则拒绝生成，提醒用户提交频繁，或等稍后再试
        if (redisUtil.containsValueKey("verificationCodeCreated:" + mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "验证码已发送，请查收短信或稍后再试");
        }

        // 生成验证码
        String verificationCode = VerificationCodeUtil.generateIntVerificationCode(6);
        //TODO 将验证码与手机号码关联存于Redis 中
        redisUtil.cacheValue("verificationCode:" + mobile, verificationCode, 60 * 10);
        redisUtil.cacheValue("verificationCodeCreated:" + mobile, verificationCode, 60);
        //TODO 调用 发送短信接口，发送短信
        /*SmsSingleSenderResult senderResult = SMSSendManager.getInstance().sendSMSCode("86", "18326189515", "老婆, 验证码为" + verificationCode);

        if (senderResult != null) {
            System.out.println(senderResult.toString());
        }*/
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
        if (redisUtil.containsValueKey("verificationCode:" + mobile)) {

            String verificationCode = redisUtil.getValue("verificationCode:" + mobile);
            //TODO 成功则删除Redis中对应号码的记录。 是否需要？？？
            //TODO 将手机号码进行加密返回
            if (verificationCode.equalsIgnoreCase(code)) {
                return new ResultInfo(ErrCode.OK, mobile, "注册码校验成功");
            }
        }
        return new ResultInfo(ErrCode.BAD_REQUEST, "", "验证码错误");
    }
}
