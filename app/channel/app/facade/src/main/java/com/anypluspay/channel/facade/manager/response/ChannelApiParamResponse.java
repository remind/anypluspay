package com.anypluspay.channel.facade.manager.response;

import lombok.Data;

/**
 * 渠道参数
 * @author wxj
 * 2025/6/4
 */
@Data
public class ChannelApiParamResponse {

    /**
     * ID
     */
    private String id;

    /**
     * 参数json值
     */
    private String param;
}
