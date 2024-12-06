package com.anypluspay.admin;

import lombok.Getter;

/**
 * @author wxj
 * 2024/11/5
 */
@Getter
public enum TestEnum {
    TEST("测试")
    ;

    private final String displayName;

    TestEnum(String displayName) {
        this.displayName = displayName;
    }
}
