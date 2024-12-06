package com.anypluspay.commons.terminal;

import lombok.Data;

/**
 * APP终端信息
 *
 * @author wxj
 * 2024/2/3
 */
@Data
public class AppTerminal extends Terminal {

    /**
     * 操作系统
     */
    private AppOS appOS;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * app版本
     */
    private String version;

    public AppTerminal() {
        super(TerminalType.APP);
    }

}
