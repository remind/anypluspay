package com.anypluspay.commons.terminal;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;


/**
 * @author wxj
 * 2024/2/3
 */
@Getter
public enum TerminalType implements CodeEnum {

    WEB("WEB", "浏览器"),
    GATEWAY("GATEWAY", "网关"),
    APP("APP", "移动APP")
    ;

    private String code;

    private String displayName;

    TerminalType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static TerminalType getByCode(String code) {
        for (TerminalType terminalType : TerminalType.values()) {
            if (terminalType.getCode().equals(code)) {
                return terminalType;
            }
        }
        return null;
    }
}
