package com.youye.controller.presenter;

import com.youye.model.UserInfo;
import com.youye.model.result.ResultInfo;
import com.youye.service.UserInfoService;
import com.youye.util.ErrCode;
import com.youye.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-29 16:25
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
public class UserPresenter {

    public interface IUserPresenter {
    }

    private IUserPresenter mCallback;
    private UserInfoService userInfoService;

    @Autowired
    public UserPresenter(IUserPresenter callback, UserInfoService userInfoService) {
        this.mCallback = callback;
        this.userInfoService = userInfoService;
    }

    public ResultInfo isValidUpdateUserBasicInfo(UserInfo userInfo) {
        if (userInfo == null || userInfo.getId() <= 0)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");

        if (userInfo.getAge() != null && userInfo.getAge() < 0)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "年龄必须大于0");

        if (userInfo.getEmail() != null && !StringUtil.isEmail(userInfo.getEmail()))
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "邮箱格式不正确");

        return null;
    }

    /**
     * 判断是否为正式的用户名
     * 1、用户名长度 >= 2
     * 2、正式用户名不允许为手机号码、邮箱(防止恶意占用别人的手机号码或邮箱)
     * 3、正式用户名不允许未纯数字。不允许存在汉字、特殊字符。
     * 4、用户名在库中存在唯一性。不允许存在相同的用户名。
     */
    public ResultInfo isValidFormalUsername(String username) {
        if (username.length() < 2)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户名长度大于2");

        if (StringUtil.isMobileNo(username) || StringUtil.isEmail(username))
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户名不允许为手机号码或邮箱地址");

        if (StringUtil.isNumeric(username))
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户名不允许为纯数字");

        if (!StringUtil.isLettersOrDigits(username))
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户名只允许由数字和字母组成");

        UserInfo userInfo = userInfoService.findOneByUsername(username);
        if (userInfo != null && userInfo.getUsername().equals(username)) {

            List<String> temp = new ArrayList<>();
            temp.add(username+ "11");
            temp.add(username+ "1122");
            temp.add(username+ "1212");
            return new ResultInfo(ErrCode.BAD_REQUEST, temp, "该用户名已使用");
        }

        return null;
    }

}
