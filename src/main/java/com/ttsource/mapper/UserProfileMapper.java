package com.ttsource.mapper;

import com.ttsource.model.user.UserProfileDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * **********************************************
 * <p/>
 * Date: 2018-09-13 19:45
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
@Mapper
public interface UserProfileMapper {

    void addUserProfile(UserProfileDO userProfileDO);
}
