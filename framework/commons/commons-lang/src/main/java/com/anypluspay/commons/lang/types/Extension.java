package com.anypluspay.commons.lang.types;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * 扩展参数类型
 *
 * @author wxj
 * 2025/4/7
 */
public class Extension implements Serializable {

    private final Map<String, String> keyValue;

    public Extension() {
        keyValue = new java.util.HashMap<>();
    }

    public Extension(String jsonString) {
        if (StringUtils.isNotBlank(jsonString)) {
            keyValue = JSONUtil.toBean(jsonString, Map.class);
        } else {
            keyValue = new java.util.HashMap<>();
        }
    }

    public void add(String key, String value) {
        keyValue.put(key, value);
    }

    public void addAll(Extension extension) {
        keyValue.putAll(extension.getMap());
    }

    public String get(String key) {
        return keyValue.get(key);
    }

    public Map<String, String> getMap() {
        return keyValue;
    }

    public boolean containsKey(String key) {
        return !isEmpty() && keyValue.containsKey(key);
    }

    public boolean isEmpty() {
        return keyValue == null || keyValue.isEmpty();
    }

    public String toJsonString() {
        return isEmpty() ? "" :JSONUtil.toJsonStr(keyValue);
    }

    @Override
    public String toString() {
        return "Extension{" +
                "keyValue=" + keyValue +
                '}';
    }
}
