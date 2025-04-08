package com.anypluspay.channel.facade.request;

import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.std.ExtensionDeserializer;
import com.anypluspay.commons.lang.std.ExtensionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wxj
 * 2024/9/24
 */
@Data
public class FundRequest implements Serializable {

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 扩展信息，可用在特殊判断以及路由
     */
    @JsonSerialize(using = ExtensionSerializer.class)
    @JsonDeserialize(using = ExtensionDeserializer.class)
    private Extension extension = new Extension();

    /**
     * 机构扩展信息，仅渠道网关API要使用传到机构，如微信的openid
     */
    @JsonSerialize(using = ExtensionSerializer.class)
    @JsonDeserialize(using = ExtensionDeserializer.class)
    private Extension instExt = new Extension();

}
