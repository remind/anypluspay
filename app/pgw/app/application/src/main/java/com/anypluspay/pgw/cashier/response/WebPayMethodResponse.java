package com.anypluspay.pgw.cashier.response;

import lombok.Data;

/**
 * web端收银台返回的支付方法
 * @author wxj
 * 2025/5/20
 */
@Data
public class WebPayMethodResponse {

    /**
     * 支付方式编码
     */
    private String code;

    /**
     * 支付方式名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;
}
