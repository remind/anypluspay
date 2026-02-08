package com.anypluspay.anypay.domain.pay;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付方式
 *
 * @author wxj
 * 2026/1/26
 */
@Data
public class PayMethod extends Entity {

    /**
     * 支付方式编码
     */
    private String code;

    /**
     * 支付方式名称
     */
    private String name;

    /**
     * 处理器
     */
    private String processor;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 备注
     */
    private String memo;
}
