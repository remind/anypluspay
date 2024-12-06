package com.anypluspay.account.domain.detail;

import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * @author wxj
 * 2023/12/17
 */
@Data
public class OuterSubAccountDetail extends Entity {

    /**
     * 明细流水号
     */
    private String voucherNo;

    /**
     * 请求号
     */
    private String requestNo;

    /**
     * 帐号
     */
    private String accountNo;

    /**
     * 发生金额
     */
    private Money amount = new Money();

    /**
     * 资金类型
     */
    private String fundType;

    /**
     * 入账前余额
     */
    private Money beforeBalance = new Money();

    /**
     * 入账后余额
     */
    private Money afterBalance = new Money();

    /**
     * 备注
     **/
    private String memo;
}
