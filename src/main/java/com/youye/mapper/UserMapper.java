package com.youye.mapper;

import com.youye.model.UserInfo;
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

    void addUser(UserInfo user);

    /**
     * 根据用户ID或用户名删除用户
     */
    void deleteUserByIdOrUsername(@Param("userId") Integer userId,
        @Param("username") String username);

    /**
     * 修改用户信息,不包括密码，手机号码
     */
    void updateUser(UserInfo userInfo);

    /**
     * 修改用户名
     */
    void updateUsername(UserInfo userInfo);

    void updatePassword(@Param("password") String password, @Param("userId") Long userId);

    void updateMobile(@Param("mobile") String mobile, @Param("userId") Long userId);

    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    @Select(
        "SELECT admin_user.*, admin_anchor.charge, admin_anchor.talk_total FROM admin_user LEFT JOIN admin_anchor on admin_user.id=admin_anchor.user_id"
            +
            " where admin_user.id = #{userId}")
    UserInfo findOneById(@Param("userId") Integer userId);

    @Select(
        "SELECT admin_user.*, admin_anchor.charge, admin_anchor.talk_total FROM admin_user LEFT JOIN admin_anchor on admin_user.id=admin_anchor.user_id"
            +
            " where admin_user.username = #{username}")
    UserInfo findOneByUsername(@Param("username") String username);

    @Select(
        "SELECT admin_user.*, admin_anchor.charge, admin_anchor.talk_total FROM admin_user LEFT JOIN admin_anchor on admin_user.id=admin_anchor.user_id"
            +
            " where admin_user.username = #{mobile} or admin_user.mobile = #{mobile}")
    UserInfo findOneByMobile(@Param("mobile") String mobile);

    List<UserInfo> findAllUsers();

}
