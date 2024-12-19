package com.anypluspay.channel.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 任务状态
 * @author wxj
 * 2024/12/13
 */
@Getter
public enum TaskStatus implements CodeEnum {
    LOCK("L", "锁定"),
    UNLOCK("U", "未锁定"),
    ;

    private final String code;

    private final String displayName;

    TaskStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}