package com.anypluspay.testtrade.facade.request;

import lombok.Data;

/**
 * 支付方式
 * @author wxj
 * 2025/4/3
 */
@Data
public class PayMethod {

    /**
     * 支付方式编码
     */
    private String code;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 资产类型
     */
    private String assetType;

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 支付机构扩展信息
     */
    private String instExtra;

    /**
     * 金额
     */
    private String amount;
}
