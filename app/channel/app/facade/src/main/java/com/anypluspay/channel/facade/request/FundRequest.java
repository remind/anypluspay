package com.anypluspay.channel.facade.request;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxj
 * 2024/9/24
 */
@Data
public class FundRequest {

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 扩展字段
     */
    private Map<String, String> extra = new HashMap<>();

    /**
     * 机构扩展信息，渠道网关API要使用的
     */
    private Map<String, String> instExtra = new HashMap<>();
}
