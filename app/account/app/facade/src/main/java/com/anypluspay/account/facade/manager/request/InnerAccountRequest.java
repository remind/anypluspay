package com.anypluspay.account.facade.manager.request;

import com.anypluspay.commons.validator.AddValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wxj
 * 2023/12/23
 */
@Data
public class InnerAccountRequest {

    /**
     * 账户号
     */
    @NotNull
    private String accountNo;

    /**
     * 账户名称
     */
    @NotNull
    private String accountName;

    /**
     * 科目编码
     */
    @NotNull(groups = AddValidate.class)
    private String titleCode;

    /**
     * 币种代码
     */
    @NotNull(groups = AddValidate.class)
    private String currencyCode;

    /**
     * 备注
     */
    @NotNull
    private String memo;
}
