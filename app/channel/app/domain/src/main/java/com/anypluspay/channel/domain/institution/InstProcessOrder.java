package com.anypluspay.channel.domain.institution;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstProcessOrderStatus;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;

import java.util.Map;

/**
 * 机构过程单
 * @author wxj
 * 2024/6/28
 */
@Data
public class InstProcessOrder extends Entity {

    /**
     * 过程单ID
     */
    private String instProcessId;

    /**
     * 机构订单ID
     */
    private String instOrderId;

    /**
     * 渠道编码
     */
    private String fundChannelCode;

    /**
     * 渠道API类型
     */
    private ChannelApiType apiType;

    /**
     * 机构订单结果状态
     */
    private InstProcessOrderStatus status;

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
    private  Map<String, String> extra;

}
