package com.ttsource.service.impl;

import com.ttsource.constant.IdentifyType;
import com.ttsource.mapper.UserAuthMapper;
import com.ttsource.mapper.UserMapper;
import com.ttsource.mapper.UserProfileMapper;
import com.ttsource.model.user.RegisterVO;
import com.ttsource.model.user.UserAuthDO;
import com.ttsource.model.user.UserDO;
import com.ttsource.model.user.UserDTO;
import com.ttsource.model.user.UserDetailDTO;
import com.ttsource.model.user.UserInfoDTO;
import com.ttsource.model.user.UserModifyVO;
import com.ttsource.model.user.UserProfileDO;
import com.ttsource.service.UserInfoService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service(value = "userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    private Logger mLogger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private UserMapper userMapper;
    private UserAuthMapper userAuthMapper;
    private UserProfileMapper userProfileMapper;

    @Autowired
    public UserInfoServiceImpl(UserMapper userMapper, UserAuthMapper userAuthMapper, UserProfileMapper userProfileMapper) {
        this.userMapper = userMapper;
        this.userAuthMapper = userAuthMapper;
        this.userProfileMapper = userProfileMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createUser(RegisterVO registerVO) {

        UserDO userDO = new UserDO();
        userDO.setNickname(registerVO.getNickname());
        userDO.setLogged(0);
        userDO.setState(0);
        Date date = new Date();
        userDO.setGmtCreate(date);
        userDO.setGmtModified(date);
        userMapper.createUser(userDO);

        long userId = userDO.getUserId();
        if (userId <= 0)
            throw new RuntimeException("add user basic info failed.");

        UserAuthDO userAuthDO = new UserAuthDO();
        userAuthDO.setUserId(userId);
        userAuthDO.setIdentifyType(registerVO.getIdentifyType());
        userAuthDO.setIdentifier(registerVO.getIdentify());
        userAuthDO.setCredential(registerVO.getCredential());
        userAuthDO.setCreateDate(date);
        userAuthDO.setModifyDate(date);
        //TODO insert user auth into database.
        userAuthMapper.addUserAuth(userAuthDO);

        //TODO create user profile and insert into database.
        UserProfileDO userProfileDO = new UserProfileDO();
        userProfileDO.setUserId(userId);
        if (IdentifyType.MOBILE.equalsIgnoreCase(registerVO.getIdentifyType())) {
            userProfileDO.setMobile(registerVO.getIdentify());
        }
        userProfileDO.setGmtCreate(date);
        userProfileDO.setGmtModified(date);
        userProfileMapper.addUserProfile(userProfileDO);

        return true;
    }

    //TODO
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
    public boolean updateUserBasicInfo(UserModifyVO userInfo) {
        try {
            UserDO userDO = new UserDO();
            userDO.setUserId(userInfo.getUserId());
            userDO.setNickname(userInfo.getNickname());
            userDO.setAge(userInfo.getAge());
            userDO.setHeight(userInfo.getHeight());
            userDO.setSex(userInfo.getSex());
            userDO.setGmtModified(new Date());
            userMapper.updateUserBasicInfo(userDO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO
    @Override
    public boolean updatePassword(String password, Long userId) {
        try {
            userMapper.updatePassword(password, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO
    @Override
    public boolean updateMobile(String mobile, Long userId) {
        try {
            userMapper.updateMobile(mobile, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO
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
    public boolean updateUserLoginState(Long userId, Integer loginState) {
        try {
            Date date = new Date();
            userMapper.updateUserLoginState(userId, loginState, date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDTO findUserById(Long userId) {
        UserDO userDO = userMapper.findUserById(userId);
        return (UserDTO) userDO;
    }

    @Override
    public List<UserInfoDTO> findAllAnchor() {
        return userMapper.listUserInfo();
    }

    @Override
    public UserAuthDO getAuthByIdentifier(String mobile) {
        return userAuthMapper.getByIdentifier(mobile);
    }

    @Override
    public UserInfoDTO findUserInfoByIdentifier(String identifier) {
        return userMapper.findUserInfoByIdentifier(identifier);
    }

    @Override
    public UserDetailDTO findUserDetailById(Long userId) {
        try {
            return userMapper.findUserDetailById(userId);
        } catch (Exception e) {
            mLogger.error(e.getMessage());
            return null;
        }
    }
}
