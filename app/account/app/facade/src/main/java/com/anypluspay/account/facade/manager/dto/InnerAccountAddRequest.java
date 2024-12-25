package com.anypluspay.account.facade.manager.dto;

import lombok.Data;

/**
 * @author wxj
 * 2023/12/23
 */
@Data
public class InnerAccountAddRequest {

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 科目编码
     */
    private String titleCode;

    /**
     * 币种代码
     */
    private String currencyCode;

    /**
     * 备注
     */
    private String memo;
}
