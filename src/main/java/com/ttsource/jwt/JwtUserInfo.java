package com.ttsource.jwt;

import com.ttsource.model.user.UserInfoDTO;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-15 10:38
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
public class JwtUserInfo implements UserDetails {
    private final String identifier;
    private final String credential;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Date passwordLastResetDate;

    public JwtUserInfo(
        String identifier,
        String credential,
        Collection<? extends GrantedAuthority> authorities,
        Date passwordLastResetDate) {
        this.identifier = identifier;
        this.credential = credential;
        this.authorities = authorities;
        this.passwordLastResetDate = passwordLastResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return credential;
    }

    @Override
    public String getUsername() {
        return identifier;
    }

    /**
     * 账号是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账号密码是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否激活
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    Date getLastPasswordResetDate() {
        return passwordLastResetDate;
    }
}
