package com.anypluspay.channel.types.order;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 机构过程订单状态，主要用于每次请求机构后返回的订单状态，和机构订单状态是有映射关系
 * @author wxj
 * 2024/7/13
 */
@Getter
public enum InstProcessOrderStatus implements CodeEnum {
    INIT("IN", "初始化"),
    SUCCESS("SU", "成功"),
    FAILED("FA", "失败"),
    PROCESSING("PR", "处理中"),
    UNKNOWN("UK", "未知"),
    ;

    private final String code;

    private final String displayName;

    InstProcessOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
