package com.anypluspay.commons.lang.utils;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * @author wxj
 * 2023/12/27
 */
public class EnumUtil {

    /**
     * 获取枚举实例
     *
     * @param enumCls
     * @param code
     * @param <T>
     * @return
     */
    public static <T extends Enum<?> & CodeEnum> T getByCode(Class<T> enumCls, String code) {
        T[] enumConstants = enumCls.getEnumConstants();
        for (T t : enumConstants) {
            if (t.getCode().equals(code)) return t;
        }
        return null;
    }
}
