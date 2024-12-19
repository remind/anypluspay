package com.anypluspay.channel.domain.institution;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.enums.TaskStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.SubmitTimeType;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 机构订单
 *
 * @author wxj
 * 2024/7/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InstOrder extends Entity {

    /**
     * 机构订单ID
     */
    private Long instOrderId;

    /**
     * 业务订单ID
     */
    private String bizOrderId;

    /**
     * 机构请求号
     */
    private String instRequestNo;

    /**
     * 机构返回流水号
     */
    private String instResponseNo;

    /**
     * 资金渠道编码
     */
    private String fundChannelCode;

    /**
     * 主过程单接口类型
     */
    private ChannelApiType apiType;

    /**
     * 状态
     */
    private InstOrderStatus status;

    /**
     * 密钥ID
     */
    private String secretId;

    /**
     * 请求网关API要用到的信息，如微信支付要用到openid
     */
    private Map<String, String> requestExtra;

    /**
     * 返回支付时需要用到的信息，如网银支付的URL
     */
    private Map<String, String> responseExtra;

    /**
     * 提交时间类型
     */
    private SubmitTimeType submitTimeType;

    /**
     * 提交时间
     */
    private LocalDateTime submitTime;

    /**
     * 预约提交时间
     */
    private LocalDateTime bookSubmitTime;

    /**
     * 下次补单时间
     */
    private LocalDateTime nextRetryTime;

    /**
     * 任务状态
     */
    private TaskStatus taskStatus;

}

