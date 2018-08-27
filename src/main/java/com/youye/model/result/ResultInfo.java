package com.youye.model.result;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-17 15:36
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
public class ResultInfo {

    private int code;
    private Object data;
    private String des;

    public ResultInfo(int code, Object data, String des) {
        this.code = code;

        if (data instanceof String) {
            String dataStr = (String) data;
            if (dataStr.trim().equals("")) {
                data = null;
            }
        }

        this.data = data;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}
