package com.youye.mapper;

import com.youye.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    void addUser(User user);

    /**
     * 根据用户ID或用户名删除用户
     */
    void deleteUserByIdOrUsername(@Param("userId") Integer userId,
        @Param("username") String username);

    /**
     * 修改用户信息,不包括密码，手机号码
     */
    void updateUser(User user);

    void updatePassword(@Param("password") String password, @Param("userId") Long userId);

    void updateMobile(@Param("mobile") String mobile, @Param("userId") Long userId);

    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    @Select(
        "SELECT admin_user.*, admin_anchor.charge, admin_anchor.talk_total FROM admin_user LEFT JOIN admin_anchor on admin_user.id=admin_anchor.user_id"
            +
            " where admin_user.id = #{userId}")
    public User findOneById(@Param("userId") Integer userId);

    @Select(
        "SELECT admin_user.*, admin_anchor.charge, admin_anchor.talk_total FROM admin_user LEFT JOIN admin_anchor on admin_user.id=admin_anchor.user_id"
            +
            " where admin_user.username = #{username}")
    public User findOneByUsername(@Param("username") String username);

    public List<User> findAllUsers();

}
