package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.facade.OrderQueryFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author wxj
 * 2024/8/23
 */
@DubboService
public class OrderQueryFacadeImpl extends AbstractFundService implements OrderQueryFacade {
    @Override
    public FundResult queryByOrderId(String orderId, boolean isInstQuery) {
        BaseBizOrder bizOrder = bizOrderRepository.load(orderId);
        AssertUtil.notNull(bizOrder, "订单不存在");
        if (bizOrder.getStatus() == BizOrderStatus.PROCESSING) {
            return applyInstProcess(bizOrder, ChannelApiType.SINGLE_QUERY);
        } else {
            return (FundResult) queryResultByOrderId(orderId);
        }
    }

    @Override
    public FundResult queryByInstOrderId(String instOrderId, boolean isInstQuery) {
        InstOrder instOrder = instOrderRepository.load(instOrderId);
        AssertUtil.notNull(instOrder, "订单不存在");
        return queryByOrderId(instOrder.getBizOrderId(), isInstQuery);
    }
}
