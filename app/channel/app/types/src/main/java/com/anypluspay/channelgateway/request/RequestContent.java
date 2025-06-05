package com.anypluspay.channelgateway.request;

import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.std.ExtensionDeserializer;
import com.anypluspay.commons.lang.std.ExtensionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求内容
 *
 * @author wxj
 * 2024/9/16
 */
@Data
public class RequestContent implements Serializable {

    /**
     * api参数ID
     */
    private String apiParamId;

    /**
     * 机构订单ID
     */
    private Long instOrderId;

    /**
     * 机构请求号
     */
    private String instRequestNo;

    /**
     * 目标机构
     */
    private String targetInst;

    /**
     * 扩展信息
     */
    @JsonSerialize(using = ExtensionSerializer.class)
    @JsonDeserialize(using = ExtensionDeserializer.class)
    private Extension extension = new Extension();

    public String getExtValue(ChannelExtKey channelExtKey) {
        return extension.get(channelExtKey.getCode());
    }

    public String getExtValue(String extKey) {
        return extension.get(extKey);
    }
}
