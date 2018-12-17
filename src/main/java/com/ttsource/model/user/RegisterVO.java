package com.ttsource.model.user;

import java.util.Date;

/**
 * **********************************************
 * <p/>
 * Date: 2018-09-12 21:19
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 页面传输过来的用户注册信息
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class RegisterVO {

    private long userId;
    /**
     * 注册方式 手机注册
     */
    private String identifyType;

    /**
     * 账号名，唯一不可修改
     */
    private String identify;
    private String credential;
    private String mobile;
    private String nickname;
    private Date gmtCreate;
    private Date gmtModified;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
