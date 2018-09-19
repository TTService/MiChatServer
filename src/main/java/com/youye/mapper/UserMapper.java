package com.youye.mapper;

import com.youye.model.user.UserDO;
import com.youye.model.user.UserDetailDTO;
import com.youye.model.user.UserInfoDTO;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * **********************************************
 * <p/>
 * Date: 2018-04-26 16:05
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
public interface UserMapper {

    void createUser(UserDO userDO);

    /**
     * 根据用户ID或用户名删除用户
     */
    void deleteUserByIdOrUsername(@Param("userId") Integer userId,
        @Param("username") String username);

    /**
     * 修改用户信息,不包括密码，手机号码
     */
    void updateUserBasicInfo(UserDO userInfo);

    void updatePassword(@Param("password") String password, @Param("userId") Long userId);

    void updateMobile(@Param("mobile") String mobile, @Param("userId") Long userId);

    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    /**
     * 更新用户的登录状态
     * @param userId 所更新的用户ID
     * @param loginState 登录状态 1 已登录， 0未登录
     */
    void updateUserLoginState(@Param("userId") long userId, @Param("loginState") Integer loginState, @Param("gmtLogin") Date gmtLogin);

    UserDO findUserById(@Param("userId") Long userId);

    UserInfoDTO findUserInfoByIdentifier(@Param("identifier") String identifier);

    List<UserInfoDTO> listUserInfo();

    UserDetailDTO findUserDetailById(@Param("userId") Long userId);
}
