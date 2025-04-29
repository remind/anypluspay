package com.anypluspay.channel.facade.fund.validator;

import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.RefundOrderRepository;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2024/8/21
 */
@Component
public class FundValidatorService {

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    /**
     * 退款校验
     * @param refundOrder   退款订单
     * @param origOrder     原入款单
     */
    public void refundValidate(RefundOrder refundOrder, FundInOrder origOrder) {
        List<RefundOrder> allRefundOrders = refundOrderRepository.loadByOrigOrderId(refundOrder.getOrigOrderId());
        if (CollectionUtils.isEmpty(allRefundOrders)) {
            if (refundOrder.getAmount().greaterThan(origOrder.getAmount())) {
                throw new BizException("退款金额不能大于原单金额");
            }
        } else {
            Money refundMount = allRefundOrders.stream().filter(ro -> ro.getStatus() == BizOrderStatus.SUCCESS)
                    .map(RefundOrder::getAmount)
                    .reduce(Money::add).orElse(new Money(0));
            if (refundMount.add(refundOrder.getAmount()).greaterThan(origOrder.getAmount())) {
                throw new BizException("退款金额已经超过可退金额");
            }
        }
    }
}
