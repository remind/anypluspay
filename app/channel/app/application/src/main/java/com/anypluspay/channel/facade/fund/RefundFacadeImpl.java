package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.commons.lang.utils.ExtUtil;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author wxj
 * 2024/7/18
 */
@DubboService
public class RefundFacadeImpl extends AbstractFundService implements RefundFacade {

    @Override
    public FundResult apply(RefundRequest request) {
        RefundOrder refundOrder = fundOrderBuilder.buildRefund(request);
        FundInOrder fundOrder = (FundInOrder) bizOrderRepository.lock(refundOrder.getOrigOrderId());
        InstOrder origInstOrder = instOrderRepository.load(fundOrder.getInstOrderId());
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(origInstOrder.getFundChannelCode(), ChannelApiType.SINGLE_REFUND);
        refundOrder.setExtra(ExtUtil.addValue(refundOrder.getExtra(), ExtKey.ORIG_INST_ORDER_ID, origInstOrder.getInstOrderId()));
        refundOrder.setInstExtra(ExtUtil.addValue(refundOrder.getInstExtra(), ExtKey.ORIG_INST_REQUEST_NO, origInstOrder.getInstRequestNo()));
        refundOrder.setInstExtra(ExtUtil.addValue(refundOrder.getInstExtra(), ExtKey.ORIG_INST_RESPONSE_NO, origInstOrder.getInstResponseNo()));
        return applyInstProcess(channelApiContext, refundOrder);
    }

}
