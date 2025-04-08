package com.anypluspay.channel.domain.bizorder.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 资金类订单
 *
 * @author wxj
 * 2024/6/27
 */
@Data
public class FundInOrder extends BaseBizOrder {

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 金额
     */
    private Money amount;

    public FundInOrder() {
        super(RequestType.FUND_IN);
    }
}
