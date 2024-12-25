package com.anypluspay.account.facade.manager.dto;

import lombok.Data;

/**
 * @author wxj
 * 2024/1/4
 */
@Data
public class OuterAccountAddResponse {

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 账户号
     */
    private String accountNo;

    public OuterAccountAddResponse() {
    }

    public OuterAccountAddResponse(String memberId, String accountType, String accountNo) {
        this.memberId = memberId;
        this.accountType = accountType;
        this.accountNo = accountNo;
    }
}
