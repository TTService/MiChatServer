package com.youye.controller.presenter;

import com.youye.model.User;
import com.youye.model.result.ResultInfo;
import com.youye.util.ErrCode;
import com.youye.util.StringUtil;

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
public class LoginPresenter {

    public interface ILoginPresenter {

    }

    private ILoginPresenter mCallback;

    public LoginPresenter(ILoginPresenter callback) {
        this.mCallback = callback;
    }

    public ResultInfo isValidForAddUser(User user) {
        if (user == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "接口调用错误，请包装用户信息");

        String mobile = user.getMobile();
        if (StringUtil.isEmpty(mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "请填写校验后返回的数字作为手机号码");
        }
        // TODO 对mobile进行解密得到真正的mobile
        if (!StringUtil.isMobileNo(mobile)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "手机号码格式错误，请填写校验后返回的数字作为手机号码");
        }

        user.setMobile(mobile);
        user.setUsername(mobile);

        /*String username = user.getUsername();
        if (StringUtil.isEmpty(username)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "请填写用户名");
        }*/
        String password = user.getPassword();
        if (StringUtil.isEmpty(password)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "请填写密码");
        }

        if (password.length() < 6 || password.length() > 18) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "密码长度为6~18位");
        }

        String nickName = user.getNickName();
        /*if (StringUtil.isEmpty(nickName)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, mobile, "请填写昵称");
        }*/
        if (nickName == null)
            user.setNickName(mobile);

        return null;
    }


    public boolean isValidPassword(String password) {
        if (StringUtil.isEmpty(password)) {
            return false;
        }

        return password.length() >= 6 && password.length() <= 18;
    }
}
