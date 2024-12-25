package com.anypluspay.account.facade.manager.dto;

import lombok.Data;

/**
 * @author wxj
 * 2023/12/22
 */
@Data
public class OuterAccountAddRequest {

    /**
     * 会员号
     **/
    private String memberId;

    /**
     * 账户类型
     **/
    private String accountType;

    /**
     * 账户名称
     **/
    private String accountName;

}
