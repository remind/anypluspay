package com.anypluspay.channelgateway.types;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 回调类型
 *
 * @author wxj
 * 2025/6/4
 */
@Getter
public enum CallbackType implements CodeEnum {

    PAGE("page", "前端"),
    SERVER("server", "服务端"),
    ;

    private final String code;

    private final String displayName;

    CallbackType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}