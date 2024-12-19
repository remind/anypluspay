package com.anypluspay.channelgateway.request;

import com.anypluspay.channel.types.enums.CompanyOrPersonal;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出款内容
 *
 * @author wxj
 * 2024/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FundOutContent extends RequestContent {

    /**
     * 机构订单ID
     */
    private Long instOrderId;

    /**
     * 机构请求号
     */
    private String instRequestNo;

    /**
     * 银行代码
     */
    private String bankCode;

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
