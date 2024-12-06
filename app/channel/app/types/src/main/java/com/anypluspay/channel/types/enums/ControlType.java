package com.anypluspay.channel.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 控制请求类型
 * @author wxj
 * 2024/7/16
 */
@Getter
public enum ControlType implements CodeEnum {
    DOWNLOAD_BILL("DB", "下载账单"), // 下载对账文件
    ;

    private final String code;

    private final String displayName;

    ControlType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}