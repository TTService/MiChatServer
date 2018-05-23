package com.youye.service.impl;

import com.youye.mapper.UserMapper;
import com.youye.model.User;
import com.youye.service.UserService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * **********************************************
 * <p/>
 * Date: 2018-04-26 10:51
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
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        try {
            userMapper.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteUserByIdOrUsername(Integer userId, String username) {
        try {
            userMapper.deleteUserByIdOrUsername(userId, username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            userMapper.updateUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatePassword(String password, Long userId) {
        try {
            userMapper.updatePassword(password, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateMobile(String mobile, Long userId) {
        try {
            userMapper.updateMobile(mobile, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateEmail(String email, Long userId) {
        try {
            userMapper.updateEmail(email, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findOneById(Integer userId) {
        return userMapper.findOneById(userId);
    }

    @Override
    public User findOneByUsername(String username) {
        return userMapper.findOneByUsername(username);
    }

    @Override
    public List<User> findAllUser() {
        //return userMapper.findAllUsers();
        return null;
    }

    @Override
    public List<User> findAllAnchor() {
        return userMapper.findAllUsers();
    }

    @Override
    public List<User> findAllAdmin() {
        return null;
    }

    @Override
    public void create(String name, String nickName, Integer sex, Integer age, Integer role, Integer state, String img, String desc) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        jdbcTemplate.update("insert into user (username, nick_name, sex, age, role, state, img, description, insert_time, update_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            name, nickName, sex, age, role, state, img, desc, dateStr, dateStr);
    }

    @Override
    public void deleteById(Integer userId) {
        jdbcTemplate.update("delete from user where id = ?", userId);
    }

    @Override
    public Integer getAllUsers() {
        return jdbcTemplate.queryForObject("select count(1) from user", Integer.class);
    }

    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from user");
    }
}
