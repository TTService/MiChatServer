package com.youye.controller;

import com.youye.remote.JavaClassExecuter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************
 * <p/>
 * Date: 2018-11-26 15:41
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
@RestController(value = "/remote")
public class RemoteExecuteController {

    @RequestMapping(name = "/execute")
    public String execute() {
        try {
            File file = new File("/Users/SinPingWu/TestClass.class");
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int byteLength = bufferedInputStream.available();
            byte[] clazzBytes = new byte[byteLength];
            bufferedInputStream.read(clazzBytes);

            String result = JavaClassExecuter.execute(clazzBytes);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
