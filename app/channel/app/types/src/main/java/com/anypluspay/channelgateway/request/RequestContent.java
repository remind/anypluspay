package com.anypluspay.channelgateway.request;

import com.anypluspay.channel.types.ExtKey;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求内容
 *
 * @author wxj
 * 2024/9/16
 */
@Data
public class RequestContent implements Serializable {

    /**
     * 扩展信息
     */
    private Map<String, String> extra = new HashMap<>();

    public String getExtValue(ExtKey extKey) {
        return extra == null ? null : extra.get(extKey.getCode());
    }

    public String getExtValue(String extKey) {
        return extra == null ? null : extra.get(extKey);
    }
}
