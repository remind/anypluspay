package com.anypluspay.anypay.channel.response;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 收单订单状态
 *
 * @author wxj
 * 2026/1/23
 */
@Getter
public enum ChannelOrderStatus implements CodeEnum {

    PROCESSING("PROCESSING", "处理中"),

    SUCCESS("SUCCESS", "成功"),

    FAIL("FAIL", "失败"),
    ;

    private final String code;

    private final String displayName;

    ChannelOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}