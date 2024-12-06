package com.anypluspay.channel.infra.persistence.bizorder;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2024/8/20
 */
@Component
public class BizOrderDalOperatorContainer {

    @Autowired
    @Qualifier("fundInDalOperator")
    private BizOrderDalOperator<? extends BaseBizOrder> fundInOrderDalOperator;

    @Autowired
    @Qualifier("refundDalOperator")
    private BizOrderDalOperator<? extends BaseBizOrder> refundDalOperator;

    @SuppressWarnings("rawtypes")
    public <T extends BaseBizOrder> BizOrderDalOperator getBizOrderDalOperator(T bizOrder) {
        if (bizOrder instanceof FundInOrder) {
            return fundInOrderDalOperator;
        } else if (bizOrder instanceof RefundOrder) {
            return refundDalOperator;
        }
        return null;
    }
}
