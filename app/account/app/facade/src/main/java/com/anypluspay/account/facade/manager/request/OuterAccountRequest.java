package com.anypluspay.account.facade.manager.request;

import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 外部户请求
 * @author wxj
 * 2023/12/22
 */
@Data
public class OuterAccountRequest {

    /**
     * 账户号
     **/
    @NotNull(groups = UpdateValidate.class)
    private String accountNo;

    /**
     * 会员号
     **/
    @NotNull(groups = AddValidate.class)
    private String memberId;

    /**
     * 账户类型
     **/
    @NotNull(groups = AddValidate.class)
    private String accountType;

    /**
     * 账户名称
     **/
    @NotNull
    private String accountName;

}
