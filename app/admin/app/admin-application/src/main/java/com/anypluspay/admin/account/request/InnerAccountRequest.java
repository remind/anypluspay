package com.anypluspay.admin.account.request;

import lombok.Data;

/**
 * @author wxj
 * 2025/1/5
 */
@Data
public class InnerAccountRequest {

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 科目号
     */
    private String titleCode;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 当前余额方向 D:借，C:贷
     */
    private String currentBalanceDirection;

    /**
     * 货币类型
     */
    private String currencyCode;

}
