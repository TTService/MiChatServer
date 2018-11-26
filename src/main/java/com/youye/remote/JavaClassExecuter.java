package com.youye.remote;

import java.lang.reflect.Method;

/**
 * **********************************************
 * <p/>
 * Date: 2018-11-23 19:48
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: JavaClass执行工具
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class JavaClassExecuter {

    /**
     * 执行外部传过来的代表一个Java类的Byte数组
     *
     * 将输入类的byte数组中代表java.lang.System 的 CONSTANT_Utf8_info 常量修改为劫持后的 HackSystem类
     * 执行方法为该类的static main(String[] args)方法, 输出结果为该类向 System.out/err输出的信息
     * @param classByte 代表一个Java类的 byte数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();

        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUtf8Constant("java/lang/System", "com/youye/remote/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("main", new Class[] { String[].class});
            method.invoke(null, new String[] {null});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HackSystem.getBufferString();
    }
}
