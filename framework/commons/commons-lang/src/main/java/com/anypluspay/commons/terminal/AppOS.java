package com.anypluspay.commons.terminal;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum AppOS implements CodeEnum {

    IOS("IOS", "苹果"),
    ANDROID("ANDROID", "安卓");

    private final String code;
    private final String displayName;

    AppOS(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
