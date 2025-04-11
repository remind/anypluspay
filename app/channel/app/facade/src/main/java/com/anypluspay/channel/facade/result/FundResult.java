package com.anypluspay.channel.facade.result;

import com.anypluspay.commons.lang.std.ExtensionDeserializer;
import com.anypluspay.commons.lang.std.ExtensionSerializer;
import com.anypluspay.commons.lang.types.Extension;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2024/7/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FundResult extends ChannelResult {

    /**
     * 机构请求号
     */
    private String instRequestNo;

    /**
     * 机构返回流水号
     */
    private String instResponseNo;

    /**
     * 是否需要清算
     */
    private boolean needClearing;

    /**
     * 待清算账户
     */
    private String clearingAccountNo;

    /**
     * 扩展返回信息，支付时可能会用到的，如网银支付的URL
     */
    @JsonSerialize(using = ExtensionSerializer.class)
    @JsonDeserialize(using = ExtensionDeserializer.class)
    private Extension responseExt = new Extension();
}
