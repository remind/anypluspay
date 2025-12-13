package com.anypluspay.anypay.types.account;


import com.anypluspay.commons.response.ResultCode;

/**
 * @author wxj
 * 2023/12/16
 */
public enum AccountResultCode implements ResultCode {

    ACCOUNT_NOT_EXISTS("ACCOUNT_NOT_EXISTS","账户不存在"),
    ACCOUNT_LOCK_TIME_OUT("ACCOUNT_LOCK_TIME_OUT", "账户锁定超时"),
    SUB_ACCOUNT_NOT_EXISTS("SUB_ACCOUNT_NOT_EXISTS", "不存在指定的子账户"),
    CR_DR_NOT_EXISTS("CR_DR_NOT_EXISTS", "不存借贷方向"),
    CR_DR_NOT_BALANCE("CR_DR_NOT_BALANCE", "借贷不平衡"),
    ACCOUNT_BALANCE_NOT_ENOUGH("ACCOUNT_BALANCE_NOT_ENOUGH","账户余额不足"),
    ACCOUNT_FAMILY_ILLEGAL("ACCOUNT_FAMILY_ILLEGAL","账户类型非法"),
    ACCOUNT_DENY_IN("ACCOUNT_DENY_IN", "账户禁止流入"),
    ACCOUNT_DENY_OUT("ACCOUNT_DENY_OUT", "账户禁止流出"),
    ACCOUNT_DENY_IN_OUT("ACCOUNT_DENY_IN_OUT", "账户止入止出"),
    ;

    private final String code;

    private final String message;

    AccountResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
