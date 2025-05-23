package com.anypluspay.commons.lang.utils;

import cn.hutool.core.lang.UUID;

/**
 * 字符串处理类
 * @author wxj
 * 2023/12/10
 */
public class StringUtil {

    /**
     * 生成随机ID
     * @return
     */
    public static String randomId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 从右边补足
     * @param str
     * @param size
     * @return
     */
    public static String alignRight(String str, int size) {
        return alignRight(str, size, ' ');
    }

    /**
     * 从右边补足
     * @param str
     * @param size
     * @param padChar
     * @return
     */
    public static String alignRight(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.getBytes().length;
            return pads <= 0 ? str : alignRight(str, size, String.valueOf(padChar));
        }
    }

    /**
     * 从右边补足
     * @param str
     * @param size
     * @param padStr
     * @return
     */
    public static String alignRight(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (padStr == null || padStr.isEmpty()) {
                padStr = " ";
            }

            int padLen = padStr.getBytes().length;
            int strLen = str.getBytes().length;
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }
}
