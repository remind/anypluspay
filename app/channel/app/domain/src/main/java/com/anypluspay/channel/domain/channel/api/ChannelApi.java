package com.anypluspay.channel.domain.channel.api;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.ChannelApiProtocol;
import lombok.Data;

import java.util.Map;

/**
 * 渠道API
 * @author wxj
 * 2024/6/27
 */
@Data
public class ChannelApi {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 类型
     */
    private ChannelApiType type;

    /**
     * 协议
     */
    private ChannelApiProtocol protocol;

    /**
     * 地址
     */
    private String address;

    /**
     * 请求流水号模式
     */
    private ApiRequestNoMode apiRequestNoMode;

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 扩展信息
     */
    private Map<String, String> extra;

    /**
     * 备注
     */
    private String memo;
}
