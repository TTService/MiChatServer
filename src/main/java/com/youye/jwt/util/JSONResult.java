package com.youye.jwt.util;

import org.json.JSONObject;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-04 16:10
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
public class JSONResult {

    public static String fillResultString(Integer status, String message, Object result){
        JSONObject jsonObject = new JSONObject(){{
            put("status", status);
            put("message", message);
            put("result", result);
        }};

        return jsonObject.toString();
    }
}
