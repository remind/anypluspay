package com.anypluspay.channel.facade.request;

import com.anypluspay.channel.types.enums.CompanyOrPersonal;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流出请求
 * @author wxj
 * 2024/9/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FundOutRequest extends FundRequest {

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户类型
     */
    private CompanyOrPersonal accountType;
}
