package com.youye.service;

import com.youye.model.User;
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
public interface UserService {

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
