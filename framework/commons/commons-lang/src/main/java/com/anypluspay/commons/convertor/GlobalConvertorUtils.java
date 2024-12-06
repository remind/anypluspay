package com.anypluspay.commons.convertor;

import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * @author wxj
 * 2024/12/5
 */
public class GlobalConvertorUtils {

    public static final String LIST_SEPARATOR = ",";

    public static List<String> toList(String str) {
        return StrUtil.split(str, LIST_SEPARATOR);
    }

    public static String toStr(List<String> list) {
        return StrUtil.join(LIST_SEPARATOR, list);
    }
}
