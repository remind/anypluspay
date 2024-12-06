package com.anypluspay.channel.domain.result;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstProcessOrderStatus;
import lombok.Data;

/**
 * 渠道结果码
 * @author wxj
 * 2024/7/9
 */
@Data
public class ApiResultCode {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * api类型
     */
    private ChannelApiType apiType;

    /**
     * 机构API结果编码
     */
    private String instApiCode;

    /**
     * 机构API结果子编码
     */
    private String instApiSubCode;

    /**
     * 渠道结果消息
     */
    private String instApiMessage;

    /**
     * 统一结果码
     */
    private String unityCode;

    /**
     * 渠道结果状态
     */
    private InstProcessOrderStatus resultStatus;

    /**
     * 是否映射
     */
    private boolean useMapping;

    /**
     * 是否可重路由
     */
    private boolean reRouteEnable;
}
