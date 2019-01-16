package com.ttsource.jwt;

import com.ttsource.model.user.UserInfoDTO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-15 10:48
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
public class JwtUserFactory {

    private JwtUserFactory() {

    }

    public static JwtUserInfo create(UserInfoDTO userInfo) {
        return new JwtUserInfo(
            userInfo.getIdentifier(),
            userInfo.getCredential(),
            mapToGrantedAuthorities(userInfo.getRoles()),
            userInfo.getGmtPswLastReset());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
            .map(SimpleGrantedAuthority :: new)
            .collect(Collectors.toList());
    }
}
