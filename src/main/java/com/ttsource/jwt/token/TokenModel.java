package com.ttsource.jwt.token;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author ScienJus
 * @date 2015/7/31.
 */
public class TokenModel {

    //用户id
    private long userId;

    //用户名
    private String identifier;

    //随机生成的uuid
    private String token;

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public TokenModel(String identifier, String token) {
        this.identifier = identifier;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
