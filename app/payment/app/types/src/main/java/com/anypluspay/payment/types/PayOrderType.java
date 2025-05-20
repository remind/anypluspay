package com.anypluspay.payment.types;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 支付指令类型
 * @author wxj
 * 2024/1/18
 */
@Getter
public enum PayOrderType implements CodeEnum {

    PAY("PAY", IdType.PAY_ORDER_ID, "普通支付"),
    REFUND("REFUND", IdType.REFUND_ORDER_ID, "退款单"),

    ;

    private String code;

    private IdType idType;

    private String displayName;

    PayOrderType(String code, IdType idType, String displayName) {
        this.code = code;
        this.idType = idType;
        this.displayName = displayName;
    }
}