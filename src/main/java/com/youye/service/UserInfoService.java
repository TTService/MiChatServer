package com.youye.service;

import com.youye.model.user.RegisterVO;
import com.youye.model.user.UserAuthDO;
import com.youye.model.user.UserDTO;
import com.youye.model.user.UserDetailDTO;
import com.youye.model.user.UserInfoDTO;
import com.youye.model.user.UserModifyVO;
import java.util.HashMap;
import java.util.List;

/**
 * **********************************************
 * <p/>
 * Date: 2018-04-26 10:48
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
public interface UserInfoService {

    boolean createUser(RegisterVO registerVO);

    /**
     * 根据用户ID或用户名删除用户
     */
    boolean deleteUserByIdOrUsername(Integer userId, String username);

    /**
     * 修改用户基本信息,不包括密码，手机号码, 用户名, 角色类型, 使用状态, 图像
     *
     */
    boolean updateUserBasicInfo(UserModifyVO userInfo);

    boolean updatePassword(String password, Long userId);

    boolean updateMobile(String mobile, Long userId);

    boolean updateEmail(String email, Long userId);

    /**
     * 更新用户的登录状态
     */
    boolean updateUserLoginState(Long userId, Integer loginState);

    UserDTO findUserById(Long userId);

    /**
     * 查找所有主播
     */
    List<UserInfoDTO> findAllAnchor();

    /**
     * 通过用户的手机号码来查找用户，理论上一个手机号码在系统只允许指定一个用户。
     * @param identifier 用户的手机号码，或者用户名
     */
    UserAuthDO getAuthByIdentifier(String identifier);

    /**
     * 查找用户简要信息
     * @param identifier 用户的账号，mobile / username / email
     */
    UserInfoDTO findUserInfoByIdentifier(String identifier);

    /**
     * 查找用户的详细信息
     */
    UserDetailDTO findUserDetailById(Long userId);
}
