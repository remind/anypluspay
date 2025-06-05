package com.anypluspay.channel.domain.channel.api;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 渠道接口的参数信息
 *
 * @author wxj
 * 2024/9/19
 */
@Data
public class ChannelApiParam extends Entity {

    /**
     * ID
     */
    private String id;

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 参数json值
     */
    private String param;

    /**
     * 备注
     */
    private String memo;
}
