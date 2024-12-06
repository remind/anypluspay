package com.anypluspay.channel.types.order;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/8/16
 */
@Getter
public enum DelayOrderStatus implements CodeEnum {
    WAIT("W", "待提交"),
    LOCK("L", "锁定"),
    FINISH("F", "完成"),
    ;

    private final String code;

    private final String displayName;

    DelayOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}