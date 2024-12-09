package com.anypluspay.channelgateway.request;

import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 网关订单信息
 *
 * @author wxj
 * 2024/9/13
 */
@Data
public class GatewayOrder extends RequestContent {

    /**
     * 机构订单ID
     */
    private String instOrderId;

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
    private Money amount;

    /**
     * 提交时间
     */
    private LocalDateTime gmtSubmit;

    /**
     * 后端回调地址
     */
    private String serverNotifyUrl;

    /**
     * 扩展信息
     */
    private Map<String, String> extra = new HashMap<>();

    public String getExtValue(ExtKey extKey) {
        return extra == null ? null : extra.get(extKey.getCode());
    }

    public String getExtValue(String extKey) {
        return extra == null ? null : extra.get(extKey);
    }
}
