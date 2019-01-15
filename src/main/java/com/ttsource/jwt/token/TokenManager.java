package com.ttsource.jwt.token;

/**
 * 对Token进行操作的接口
 * @author ScienJus
 * @date 2015/7/31.
 */
public interface TokenManager {

    static final String TOKEN = "Authorization";

    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    TokenModel createToken(long userId);

    /**
     * 创建一个token关联上指定用户
     * @param identifier 指定用户的账户名例如username, weChat
     * @return 生成的token
     */
    TokenModel createToken(String identifier);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(TokenModel model);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     */
    TokenModel getToken(String authentication);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    void deleteToken(long userId);

    /**
     * 生成Token
     *
     * 根据用户的名称 + 加上UUID生成Token，并采用MD5获取摘要定义为Token
     *
     * @return 生成的Token
     */
    String generateToken(String identifier);

}
