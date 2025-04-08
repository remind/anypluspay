package com.anypluspay.commons.lang.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.anypluspay.commons.enums.CodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxj
 * 2024/8/19
 */
public class ExtUtil {

    /**
     * 获取扩展字段值
     *
     * @param extKey
     * @param extInfo
     * @return
     */
    public static Object getValue(CodeEnum extKey, Map<String, String> extInfo) {
        return extInfo == null ? null : extInfo.get(extKey.getCode());
    }

    /**
     * 获取扩展字段值
     *
     * @param extKey
     * @param extInfo
     * @return
     */
    public static Object getValue(String extKey, Map<String, String> extInfo) {
        return extInfo == null ? null : extInfo.get(extKey);
    }

    /**
     * 获取String类型的扩展字段值
     *
     * @param extKey
     * @param extInfo
     * @return
     */
    public static String getStringValue(CodeEnum extKey, Map<String, String> extInfo) {
        Object o = getValue(extKey, extInfo);
        return o == null ? "" : String.valueOf(o);
    }

    /**
     * 获取String类型的扩展字段值
     *
     * @param extKey
     * @param extInfo
     * @return
     */
    public static String getStringValue(String extKey, Map<String, String> extInfo) {
        Object o = getValue(extKey, extInfo);
        return o == null ? "" : String.valueOf(o);
    }

    /**
     * 添加扩展字段值
     *
     * @param extInfo
     * @param extKey
     * @param value
     * @return
     */
    public static Map<String, String> addValue(Map<String, String> extInfo, CodeEnum extKey, String value) {
        if (extInfo == null) {
            extInfo = new HashMap<>();
        }
        extInfo.put(extKey.getCode(), value);
        return extInfo;
    }

    /**
     * 添加扩展字段值
     *
     * @param extInfo
     * @param extKey
     * @param value
     * @return
     */
    public static Map<String, String> addValue(Map<String, String> extInfo, String extKey, String value) {
        if (extInfo == null) {
            extInfo = new HashMap<>();
        }
        extInfo.put(extKey, value);
        return extInfo;
    }

    /**
     * 合并扩展字段
     *
     * @param oldExtra
     * @param newExtra
     * @return
     */
    public static Map<String, String> merge(Map<String, String> oldExtra, Map<String, String> newExtra) {
        if (MapUtil.isNotEmpty(newExtra)) {
            if (oldExtra == null) {
                oldExtra = new HashMap<>();
            }
            oldExtra.putAll(newExtra);
        }
        return oldExtra;
    }

    /**
     * 扩展字段字符串转Map
     *
     * @param extString
     * @return
     */
    public static Map<String, String> toMap(String extString) {
        if (StringUtils.isNotBlank(extString)) {
            return JSONUtil.toBean(extString, Map.class);
        }
        return null;
    }
}
