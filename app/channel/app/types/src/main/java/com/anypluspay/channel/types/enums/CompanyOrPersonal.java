package com.anypluspay.channel.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 对公对私
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum CompanyOrPersonal implements CodeEnum {

    COMPANY("C", "对公"),
    PERSONAL("P", "对私"),
    ;

    private final String code;

    private final String displayName;

    CompanyOrPersonal(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}

