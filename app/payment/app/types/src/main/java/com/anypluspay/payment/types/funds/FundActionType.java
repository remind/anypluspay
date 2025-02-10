package com.anypluspay.payment.types.funds;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/1/17
 */
@Getter
public enum FundActionType implements CodeEnum {

    FUND_CHANGE("FUND_CHANGE", "资金变更"),

    FUND_FREEZE("FUND_FREEZE", "资金冻结解冻"),

    ;


    private String code;

    private String displayName;

    FundActionType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
