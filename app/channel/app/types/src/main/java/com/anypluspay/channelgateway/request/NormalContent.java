package com.anypluspay.channelgateway.request;

import com.anypluspay.commons.lang.std.MoneyDeserializer;
import com.anypluspay.commons.lang.std.MoneySerializer;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 常规内容
 *
 * @author wxj
 * 2024/9/13
 */
@Data
public class NormalContent extends RequestContent {

    /**
     * 机构订单ID
     */
    private Long instOrderId;

    /**
     * 机构请求号
     */
    private String instRequestNo;

    /**
     * 目标机构
     */
    private String targetInst;

    /**
     * 金额
     */
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money amount;

    /**
     * 提交时间
     */
    private LocalDateTime gmtSubmit;

    /**
     * 后端回调地址
     */
    private String serverNotifyUrl;


}
