package com.anypluspay.channel.facade.result;

import com.anypluspay.channel.types.order.BizOrderStatus;
import lombok.Data;

import java.util.Map;

/**
 * @author wxj
 * 2024/7/18
 */
@Data
public class ChannelResult {

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 渠道编码
     */
    private String fundChannelCode;

    /**
     * 状态
     */
    private BizOrderStatus status;

    /**
     * 统一结果码
     */
    private String unityCode;

    /**
     * 统一消息
     */
    private String unityMessage;

    /**
     * 扩展信息
     */
    private Map<String, Object> extInfo;
}
