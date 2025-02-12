package com.anypluspay.payment.types.funds;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 资金动作
 * @author wxj
 * 2024/1/15
 */
@Getter
public enum FundAction implements CodeEnum {

    UNFREEZE("UNFREEZE", "解冻", 4),
    FREEZE("FREEZE", "冻结", 3),
    INCREASE("INCREASE", "增加", 2),
    DECREASE("DECREASE", "减少", 1),
    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 显示名称
     */
    private final String displayName;

    /**
     * 执行顺序
     */
    private final int sort;

    FundAction(String code, String displayName, int sort) {
        this.code = code;
        this.displayName = displayName;
        this.sort = sort;
    }

    public FundAction reverse() {
        return switch (this) {
            case FREEZE -> UNFREEZE;
            case UNFREEZE -> FREEZE;
            case INCREASE -> DECREASE;
            case DECREASE -> INCREASE;
        };
    }

}
