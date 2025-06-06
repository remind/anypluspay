package com.anypluspay.payment.facade.acquiring.pay;

import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 收单订单支付校验
 * @author wxj
 * 2025/5/26
 */
@Component
public class AcquiringPayValidator {

    public void validate(AcquiringOrder acquiringOrder) {
        AssertUtil.notNull(acquiringOrder, "订单不存在");
        AssertUtil.isFalse(acquiringOrder.getStatus() == AcquiringOrderStatus.SUCCESS, "订单已成功");
        AssertUtil.isFalse(acquiringOrder.getStatus() == AcquiringOrderStatus.CLOSED, "订单已关闭");
        AssertUtil.isTrue(LocalDateTime.now().isBefore(acquiringOrder.getGmtExpire()), "订单已过期");
    }
}
