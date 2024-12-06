package com.anypluspay.channelgateway.api.sign;

import com.anypluspay.channelgateway.request.OrderInfo;
import lombok.Data;

/**
 * @author wxj
 * 2024/9/15
 */
@Data
public class SignOrderInfo extends OrderInfo {

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 机构回调地址, 后台回调
     */
    private String callbackServerUrl;

    /**
     * 机构回调地址, 前台回调
     */
    private String callbackPageUrl;
}
