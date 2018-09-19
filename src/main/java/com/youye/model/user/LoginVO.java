package com.youye.model.user;

/**
 * **********************************************
 * <p/>
 * Date: 2018-09-11 23:00
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
public class LoginVO {

    private String identifierType;
    private String identifier;
    private String credential;

    public LoginVO() {
        this.identifierType = "";
        this.identifier = "";
        this.credential = "";
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
