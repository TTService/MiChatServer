package com.youye.sms;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import java.io.IOException;
import org.json.JSONException;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-23 21:34
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
public class SMSSendManager {

    private int appId = 1400094410;
    private String appKey = "acb6c6d3e510a5bf241ffad5736db4cb";

    private static SMSSendManager s_Instance;

    public static SMSSendManager getInstance() {
        if (s_Instance == null) {
            synchronized (SMSSendManager.class) {
                if (s_Instance == null) {
                    s_Instance = new SMSSendManager();
                }
            }
        }
        return s_Instance;
    }


    public SmsSingleSenderResult sendSMSCode(String nationCode, String mobile, String msgContent) {
        try {
            SmsSingleSender smsSingleSender = new SmsSingleSender(appId, appKey);
            return smsSingleSender.send(0, nationCode, mobile, msgContent, "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            // nothing
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
