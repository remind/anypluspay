package com.anypluspay.channel.types.result;

import com.anypluspay.channel.types.channel.ChannelApiType;
import lombok.Data;

import java.io.Serializable;

/**
 * 处理结果
 * @author wxj
 * 2024/9/15
 */
@Data
public class ProcessResult implements Serializable {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 接口类型
     */
    private ChannelApiType apiType;

    /**
     * 结果码
     */
    private String apiCode;

    /**
     * 子结果码
     */
    private String apiSubCode;

    /**
     * 描述信息
     */
    private String apiMessage;
}
