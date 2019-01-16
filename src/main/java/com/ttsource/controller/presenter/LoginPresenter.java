package com.ttsource.controller.presenter;

import com.ttsource.model.result.ResultInfo;
import com.ttsource.model.user.RegisterVO;
import com.ttsource.entities.UserAuthDO;
import com.ttsource.service.UserInfoService;
import com.ttsource.util.ErrCode;
import com.ttsource.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-18 15:04
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
@Component
public class LoginPresenter {

    public interface ILoginPresenter {
    }

    private ILoginPresenter mCallback;
    private UserInfoService userInfoService;

    @Autowired
    public LoginPresenter(ILoginPresenter callback, UserInfoService userInfoService) {
        this.mCallback = callback;
        this.userInfoService = userInfoService;
    }

    public ResultInfo isValidForAddUser(RegisterVO registerVO) {
        if (registerVO == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "接口调用错误，请包装用户信息");

        String mobile = registerVO.getMobile();
        if (StringUtil.isEmpty(mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "请填写校验后返回的数字作为手机号码");
        }
        // TODO 对mobile进行解密得到真正的mobile
        if (!StringUtil.isMobileNo(mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "手机号码格式错误，请填写校验后返回的数字作为手机号码");
        }

        registerVO.setMobile(mobile);

        ResultInfo resultInfo = isValidFormalIdentifier(registerVO.getIdentify());
        if (resultInfo != null) {
            return resultInfo;
        }

        String credential = registerVO.getCredential();
        if (StringUtil.isEmpty(credential)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "请填写密码");
        }

        if (!isValidPassword(credential)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "密码长度为6~18位");
        }

        String nickname = registerVO.getNickname();
        /*if (StringUtil.isEmpty(nickName)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "请填写昵称");
        }*/
        if (nickname == null)
            registerVO.setNickname(mobile);

        return null;
    }


    private boolean isValidPassword(String password) {
        if (StringUtil.isEmpty(password)) {
            return false;
        }

        return password.length() >= 6 && password.length() <= 18;
    }

    /**
     * 判断是否为正式的账号，对应admin_user_auth中的identifier
     * 1、账号长度 >= 2
     * 2、账号不允许存在汉字、特殊字符。
     * 3、账号在库中存在唯一性。不允许存在相同的账号。
     */
    public ResultInfo isValidFormalIdentifier(String identifier) {
        if (StringUtil.isEmpty(identifier)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "请填写账号");
        }

        if (identifier.length() < 2 || identifier.length() > 18)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "账号2~18字节");

        if (!StringUtil.isAccount(identifier))
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "账号包含非法字符");

        UserAuthDO userAuthDO = userInfoService.getAuthByIdentifier(identifier);

        if (userAuthDO != null && userAuthDO.getIdentifier().equals(identifier)) {
            List<String> temp = new ArrayList<>();
            temp.add(identifier+ "11");
            temp.add(identifier+ "1122");
            temp.add(identifier+ "1212");
            return new ResultInfo(ErrCode.BAD_REQUEST, temp, "该账号已存在");
        }

        return null;
    }
}
