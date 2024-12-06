package com.anypluspay.channel.facade.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxj
 * 2024/7/12
 */
@Data
public class FundResult extends ChannelResult {

    /**
     * 机构请求号
     */
    private String instRequestNo;

    /**
     * 机构返回流水号
     */
    private String instResponseNo;

    /**
     * 扩展返回信息，支付时可能会用到的，如网银支付的URL
     */
    private Map<String, String> responseExtra = new HashMap<>();
}
