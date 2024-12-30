package com.anypluspay.account.facade.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxj
 * 2024/1/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
