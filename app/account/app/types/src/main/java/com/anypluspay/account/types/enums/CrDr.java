package com.anypluspay.account.types.enums;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 借贷方向
 *
 * @author wxj
 * 2023/12/16
 */
public enum CrDr implements CodeEnum {

    DEBIT("D", "借方") {
        @Override
        public CrDr reverse() {
            return CREDIT;
        }
    },

    CREDIT("C", "贷方") {
        @Override
        public CrDr reverse() {
            return DEBIT;
        }
    };

    /**
     * 获取反向借贷
     *
     * @return
     */
    public abstract CrDr reverse();

    private final String code;

    private final String displayName;

    CrDr(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }


}
