package com.ttsource.util;

import java.util.Random;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-18 13:54
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 验证码工具类
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class VerificationCodeUtil {

    /**
     * 获取长度为{@param length} 的验证码
     * @param length 验证码长度
     * @return 由数字组成的长度为{@param length}的验证码
     */
    public static String generateIntVerificationCode(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int tempInt = random.nextInt(9);
            builder.append(tempInt);
        }
        return builder.toString();
    }
}
