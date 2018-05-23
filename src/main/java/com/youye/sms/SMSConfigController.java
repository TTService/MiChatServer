package com.youye.sms;

import com.youye.model.result.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-23 18:39
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
@RestController
@RequestMapping("/sms")
public class SMSConfigController {

    /**
     * 添加模板
     */
    @RequestMapping("/addModule")
    public ResultInfo addModule() {
        return null;
    }
}
