package com.anypluspay.commons.lang.utils;

import com.anypluspay.commons.enums.CodeEnum;
import com.anypluspay.commons.enums.EnumObject;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 枚举对象转为EnumObject列表
     *
     * @param enumCls
     * @param <T>
     * @return
     */
    public static <T extends Enum<?> & CodeEnum> List<EnumObject> toEnumObjects(Class<T> enumCls) {
        T[] enumConstants = enumCls.getEnumConstants();
        List<EnumObject> list = new ArrayList<>();
        for (T t : enumConstants) {
            list.add(new EnumObject(t.getCode(), t.getDisplayName()));
        }
        return list;
    }
}
