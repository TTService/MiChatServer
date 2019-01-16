package com.ttsource.jwt;

import com.ttsource.mapper.UserAuthMapper;
import com.ttsource.entities.UserAuthDO;
import com.ttsource.mapper.UserMapper;
import com.ttsource.model.user.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-15 10:59
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
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public JwtUserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfoDTO userInfo = userMapper.findUserInfoByIdentifier(s);
        if (userInfo == null)
            return null;

        return JwtUserFactory.create(userInfo);

    }
}
