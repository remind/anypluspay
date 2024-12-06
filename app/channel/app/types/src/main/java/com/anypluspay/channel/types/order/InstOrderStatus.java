package com.anypluspay.channel.types.order;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 机构订单状态，由机构过程订单状态映射过来
 * 机构返回结果码 -> 机构过程订单状态 -> 机构订单状态
 *            \--> 统一状态码 --> 客户提示信息
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum InstOrderStatus implements CodeEnum {
    INIT("IN", "初始"),
    SUCCESS("SU", "成功"),
    FAILED("FA", "失败"),
    PROCESSING("PR", "处理中"),
    ;

    private final String code;

    private final String displayName;

    InstOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
