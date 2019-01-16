package com.ttsource.util;

import java.security.MessageDigest;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-09 10:46
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: MD5摘要算法 加密工具
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class MD5Util {

    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte item : b) {
            resultSb.append(byteToHexString(item));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String encode(String origin) {
        return encode(origin, "UTF8");
    }

    /**
     * MD5加密算法
     *
     * @param origin 需要加密的数据
     *
     * @param charsetName 数据编码格式
     *
     * @return 加密数据结果
     */
    public static String encode(String origin, String charsetName) {
        String resultString = origin;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
        } catch (Exception exception) {
            // nothing
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
