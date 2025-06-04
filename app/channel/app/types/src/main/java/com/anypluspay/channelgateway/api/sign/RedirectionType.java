package com.anypluspay.channelgateway.api.sign;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/6/3
 */
@Getter
public enum RedirectionType implements CodeEnum {

    PAGE_URL("pageUrl", "页面跳转"),
    FORM_CONTENT("formContent", "表单提交"),
    ;

    private final String code;

    private final String displayName;

    RedirectionType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
