package com.youye.remote;

/**
 * **********************************************
 * <p/>
 * Date: 2018-11-23 14:22
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 修改Class文件，暂时只提供修改常量池常量的功能
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class ClassModifier {

    /**
     * Class 文件中常量池的起始偏移地址
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    /**
     * CONSTANT_Utf8_info 常量的tag标志
     */
    private static final int CONSTANT_Utf8_info = 1;

    /**
     * 常量池中14中常量所占的长度，CONSTANT_Utf8_info 型常量除外，因为它不是定长的
     *
     * 每种常量都有一个tag标签，根据tag的值来获取常量所占的长度，tag最大值为18.
     */
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, 5, -1, 5, 9, 9, 3, 3, 5, 5, 5, 5, -1, -1, 4, 3, -1, 5};

    private static final int u1 = 1;

    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * 修改常量池中CONSTANT_Utf8_info常量的内容
     * @param oldStr 修改前的字符串
     * @param newStr 修改后的字符串
     * @return 修改结果
     */
    public byte[] modifyUtf8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 0; i < cpc; i++) {
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);
            if (tag == CONSTANT_Utf8_info) {
                // 获取CONSTANT_Utf8_info常量的Utf-8编码的字符串占用的字节数
                int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
                offset += (u1 + u2);
                // 获取该CONSTANT_Utf8_info常量所对应的字符串内容
                String str = ByteUtils.bytes2String(classByte, offset, len);
                if (str.equalsIgnoreCase(oldStr)) {
                    // 将newStr转换成byte[]
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    // 获取字符串长度对应的所占u2字节的byte[]
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    // 替换CONSTANT_Utf8_info常量中代表Utf-8编码的字符串所占用的字节长度
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                    // 替换字符串内容
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);

                    return classByte;
                } else {
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }

        return classByte;
    }

    /**
     * 获取常量池中常量的数量
     *
     * @return 常量池数量
     */
    public int getConstantPoolCount() {
        // 在常量池中，第9-10的两个字节代表常量的数量
        return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}
