package com.youye.service;

import com.youye.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
public interface UserService {

    void addUser(User user);

    /**
     * 根据用户ID或用户名删除用户
     */
    boolean deleteUserByIdOrUsername(Integer userId, String username);

    /**
     * 修改用户信息,不包括密码，手机号码
     */
    boolean updateUser(User user);

    boolean updatePassword(String password, Long userId);

    boolean updateMobile(String mobile, Long userId);

    boolean updateEmail(String email, Long userId);

    User findOneById(Integer userId);

    User findOneByUsername(String username);

    /**
     * 查找所有用户
     */
    List<User> findAllUser();

    /**
     * 查找所有主播
     */
    List<User> findAllAnchor();

    /**
     * 查找所有管理员用户
     */
    List<User> findAllAdmin();

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
