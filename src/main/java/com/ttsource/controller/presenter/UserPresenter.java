package com.ttsource.controller.presenter;

import com.ttsource.model.result.ResultInfo;
import com.ttsource.model.user.UserModifyVO;
import com.ttsource.util.ErrCode;

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
public class UserPresenter {

    public interface IUserPresenter {
    }

    private IUserPresenter mCallback;

    public UserPresenter(IUserPresenter callback) {
        this.mCallback = callback;
    }

    public ResultInfo isValidUpdateUserBasicInfo(UserModifyVO userInfo) {
        if (userInfo == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");

        if (userInfo.getAge() != null && userInfo.getAge() < 0)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "年龄必须大于0");

        String nickname = userInfo.getNickname();
        if (nickname != null
            && (nickname.equalsIgnoreCase("")
            || nickname.length() > 20))
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "昵称1~20个字符");

        Integer height = userInfo.getHeight();
        if (height != null && (height < 50 || height > 300)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "身高xxxCM");
        }

        Integer sex = userInfo.getSex();
        if (sex != null && (sex != 0 && sex != 1 && sex != 2)) {
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "性别不合理");
        }
        return null;
    }

}
