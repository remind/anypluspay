package com.anypluspay.channelgateway.api.sign;

import com.anypluspay.channelgateway.request.GatewayOrder;
import lombok.Data;

/**
 * @author wxj
 * 2024/9/15
 */
@Data
public class SignGatewayOrder extends GatewayOrder {

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 前端页面跳转回调
     */
    private String callbackPageUrl;
}
