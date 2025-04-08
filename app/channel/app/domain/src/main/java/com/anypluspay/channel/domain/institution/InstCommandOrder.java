package com.anypluspay.channel.domain.institution;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Extension;
import lombok.Data;

import java.util.Map;

/**
 * 机构指令单
 * @author wxj
 * 2024/6/28
 */
@Data
public class InstCommandOrder extends Entity {

    /**
     * 指令ID
     */
    private Long commandId;

    /**
     * 机构订单ID
     */
    private Long instOrderId;

    /**
     * 渠道编码
     */
    private String fundChannelCode;

    /**
     * 渠道API类型
     */
    private ChannelApiType apiType;

    /**
     * 机构订单状态
     */
    private InstOrderStatus status;

    /**
     * 统一结果码
     */
    private String unityCode;

    /**
     * 统一消息
     */
    private String unityMessage;

    /**
     * 机构API结果码
     */
    private String apiCode;

    /**
     * 机构API子结果码
     */
    private String apiSubCode;

    /**
     * 机构信息
     */
    private String apiMessage;

    /**
     * 扩展信息
     */
    private Extension extension;

}
