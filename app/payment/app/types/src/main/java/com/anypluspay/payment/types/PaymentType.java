package com.anypluspay.payment.types;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/1/15
 */
@Getter
public enum PaymentType implements CodeEnum {

    INSTANT("INSTANT", IdType.PAYMENT_INSTANT_ID ,"直接支付");;

    private String code;

    private IdType idType;

    private String displayName;

    PaymentType(String code,IdType idType, String displayName) {
        this.code = code;
        this.idType = idType;
        this.displayName = displayName;
    }
}
