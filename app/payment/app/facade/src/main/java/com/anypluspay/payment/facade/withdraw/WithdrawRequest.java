package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 提现请求
 * @author wxj
 * 2025/5/15
 */
@Data
public class WithdrawRequest {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 出款账户
     */
    private String accountNo;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 身份证号码
     */
    private String cardIdNo;

    /**
     * 银行卡姓名
     */
    private String cardName;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 扩展信息
     */
    private String extension;

    /**
     * 备注
     */
    private String memo;
}
