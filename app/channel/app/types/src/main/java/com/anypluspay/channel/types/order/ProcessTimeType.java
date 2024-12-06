package com.anypluspay.channel.types.order;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 提交时间类型
 *
 * @author wxj
 * 2024/8/16
 */
@Getter
public enum ProcessTimeType implements CodeEnum {

    REAL("R", "实时"),
    DELAYED("D", "延时");

    private final String code;

    private final String displayName;

    ProcessTimeType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
