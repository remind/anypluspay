package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.facade.OrderQueryFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单结果查询
 *
 * @author wxj
 * 2024/8/23
 */
@RestController
public class OrderQueryFacadeImpl extends AbstractFundService implements OrderQueryFacade {

    @Override
    public FundResult queryByOrderId(String orderId, boolean isInstQuery) {
        BaseBizOrder bizOrder = bizOrderRepository.load(orderId);
        AssertUtil.notNull(bizOrder, "订单不存在");
        InstOrder instOrder = instOrderRepository.load(bizOrder.getInstOrderId());
        return query(bizOrder, instOrder, isInstQuery);
    }

    @Override
    public FundResult queryByInstOrderId(Long instOrderId, boolean isInstQuery) {
        InstOrder instOrder = instOrderRepository.load(instOrderId);
        return query(null, instOrder, isInstQuery);
    }

    @Override
    public FundResult queryByInstRequestNo(String instRequestNo, boolean isInstQuery) {
        InstOrder instOrder = instOrderRepository.loadByInstRequestNo(instRequestNo);
        return query(null, instOrder, isInstQuery);
    }

    private FundResult query(BaseBizOrder bizOrder, InstOrder instOrder, boolean isInstQuery) {
        AssertUtil.notNull(instOrder, "机构订单不存在");
        if (bizOrder == null) {
            bizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
        }
        AssertUtil.notNull(bizOrder, "渠道订单不存在");
        ChannelApiType queryApiType = channelApiDomainService.getQueryApiType(instOrder.getApiType());
        if (bizOrder.getStatus() == BizOrderStatus.PROCESSING && isInstQuery) {
            return applyInstProcess(bizOrder, queryApiType);
        } else {
            return (FundResult) queryResultByOrderId(instOrder.getBizOrderId());
        }
    }
}
