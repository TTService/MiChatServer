package com.youye.remote;

/**
 * **********************************************
 * <p/>
 * Date: 2018-11-23 14:09
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 为多次载入执行类二加入的加载器
 * 把defineClass方法开放出来，只有外部显示调用的时候才会使用到loadByte方法
 * 由虚拟机调用时，任然按照原有的双亲委派规则使用loadClass方法进行类加载
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class HotSwapClassLoader extends ClassLoader{

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }
}
