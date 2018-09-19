package com.youye.jwt.security;

import com.youye.mapper.UserAuthMapper;
import com.youye.model.user.UserAuthDO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-08 09:33
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
@Service(value = "userDetailsService")
public class MiChatUserService implements UserDetailsService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserDetails loadUserByUsername(String s) {
        UserAuthDO userAuth = userAuthMapper.getByIdentifier(s);
        if (userAuth == null)
            return null;

        List<GrantedAuthorityImpl> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(userAuth.getIdentifier(), userAuth.getCredential(), authorities);
    }
}
