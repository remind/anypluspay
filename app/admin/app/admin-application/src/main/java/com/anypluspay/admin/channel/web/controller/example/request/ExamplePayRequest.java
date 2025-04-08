package com.anypluspay.admin.channel.web.controller.example.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wxj
 * 2025/4/7
 */
@Data
public class ExamplePayRequest {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 扩展信息，可用在特殊判断以及路由
     */
    private String extension;

    /**
     * 机构扩展信息，仅渠道网关API要使用传到机构，如微信的openid
     */
    private String instExt;
}
