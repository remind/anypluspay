package com.anypluspay.anypay.trade;

import com.anypluspay.anypay.types.trade.WithdrawOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * @author wxj
 * 2025/5/15
 */
@Data
public class WithdrawOrder extends BaseTradeOrder {

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
     * 状态
     */
    private WithdrawOrderStatus status;

    /**
     * 身份证号码
     */
    private String cardIdNo;

    /**
     * 银行卡姓名
     */
    private String cardName;

    /**
     * 银行代码
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
