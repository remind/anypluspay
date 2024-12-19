package com.anypluspay.channel.domain.bizorder.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.types.enums.CompanyOrPersonal;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * @author wxj
 * 2024/7/18
 */
@Data
public class FundOutOrder extends BaseBizOrder {

    /**
     * 银行编码
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

    public FundOutOrder() {
        super(RequestType.FUND_OUT);
    }
}
