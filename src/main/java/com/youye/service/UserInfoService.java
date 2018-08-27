package com.youye.service;

import com.youye.model.UserInfo;
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

    void addUser(UserInfo userInfo);

    /**
     * 根据用户ID或用户名删除用户
     */
    boolean deleteUserByIdOrUsername(Integer userId, String username);

    /**
     * 修改用户基本信息,不包括密码，手机号码, 用户名, 角色类型, 使用状态, 图像
     *
     */
    boolean updateUserBasicInfo(UserInfo userInfo);

    /**
     * 修改用户名
     */
    boolean updateFormalUsername(UserInfo userInfo);

    boolean updatePassword(String password, Long userId);

    boolean updateMobile(String mobile, Long userId);

    boolean updateEmail(String email, Long userId);

    UserInfo findOneById(Integer userId);

    UserInfo findOneByUsername(String username);

    /**
     * 通过用户的手机号码来查找用户，理论上一个手机号码在系统只允许指定一个用户。
     * @param mobile 用户的手机号码，或者用户名
     */
    UserInfo findOneByMobile(String mobile);

    /**
     * 查找所有用户
     */
    List<UserInfo> findAllUser();

    /**
     * 查找所有主播
     */
    List<UserInfo> findAllAnchor();

    /**
     * 查找所有管理员用户
     */
    List<UserInfo> findAllAdmin();

    /**
     * 创建用户
     */
    void create(String name, String nickName, Integer sex, Integer age, Integer role, Integer state,
        String img, String desc);

    /**
     * 根据用户ID删除用户
     */
    void deleteById(Integer userId);

    /**
     * 获取所有用户
     */
    Integer getAllUsers();

    /**
     * 删除所有用户
     */
    void deleteAllUsers();
}
