package com.anypluspay.channel.types.channel;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum ChannelApiProtocol implements CodeEnum {

    BEAN("BEAN", "Spring Bean"),
    HTTP("HTTP", "HTTP调用"),
    DISCOVERY("DISCOVERY", "注册发现"),
    ;

    private final String code;

    private final String displayName;

    ChannelApiProtocol(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
