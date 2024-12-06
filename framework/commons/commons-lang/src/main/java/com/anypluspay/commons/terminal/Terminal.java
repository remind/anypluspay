package com.anypluspay.commons.terminal;

import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * @author wxj
 * 2024/2/3
 */
@Data
public abstract class Terminal {

    private final TerminalType terminalType;

    public Terminal(TerminalType terminalType) {
        this.terminalType = terminalType;
    }

    public static Terminal fromJsonString(TerminalType terminalType, String jsonString) {
        switch (terminalType) {
            case APP:
                return JSONUtil.toBean(jsonString, AppTerminal.class);
            default:
                return null;
        }
    }
}
