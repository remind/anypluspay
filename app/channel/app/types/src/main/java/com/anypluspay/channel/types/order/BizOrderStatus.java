package com.anypluspay.channel.types.order;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/7/13
 */
@Getter
public enum BizOrderStatus implements CodeEnum {
    SUCCESS("SU", "成功"),
    FAILED("FA", "失败"),
    PROCESSING("PR", "处理中"),
    ;

    private final String code;

    private final String displayName;

    BizOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}