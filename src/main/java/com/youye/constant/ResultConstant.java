package com.youye.constant;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-18 10:05
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
public enum ResultConstant {

    /**
     * 失败
     */
    FAILED(500, "failed"),

    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 无效长度
     */
    INVALID_LENGTH(40001, "Invalid length"),

    /**
     * 用户名不能为空
     */
    EMPTY_USERNAME(40002, "Username cannot be empty"),

    /**
     * 密码不能为空
     */
    EMPTY_PASSWORD(40003, "Password cannot be empty"),

    /**
     * 帐号不存在
     */
    INVALID_USERNAME(40004, "Account does not exist"),

    /**
     * 密码错误
     */
    INVALID_PASSWORD(40005, "Password error"),

    /**
     * 无效帐号
     */
    INVALID_ACCOUNT(40006, "Invalid account");

    public int code;
    public String message;

    ResultConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
