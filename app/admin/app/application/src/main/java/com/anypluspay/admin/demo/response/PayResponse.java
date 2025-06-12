package com.anypluspay.admin.demo.response;

import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateResponse;
import lombok.Data;

/**
 * 支付响应
 * @author wxj
 * 2025/6/12
 */
@Data
public class PayResponse {

    /**
     * 创建的交易信息
     */
    private AcquiringCreateResponse trade;

    /**
     * 收银台地址
     */
    private String cashierUrl;
}
