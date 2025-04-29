package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.commons.exceptions.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2024/7/18
 */
@Slf4j
@Validated
@RestController
public class RefundFacadeImpl extends AbstractFundService implements RefundFacade {

    @Override
    public FundResult apply(@Validated RefundRequest request) {
        try {
            RefundOrder refundOrder = fundOrderBuilder.buildRefund(request);
            FundInOrder fundOrder = (FundInOrder) bizOrderRepository.load(refundOrder.getOrigOrderId());
            InstOrder origInstOrder = instOrderRepository.load(fundOrder.getInstOrderId());
            ChannelApiContext channelApiContext = channelRouteService.routeByChannel(origInstOrder.getFundChannelCode(), ChannelApiType.SINGLE_REFUND);
            refundOrder.getExtension().add(ChannelExtKey.ORIG_INST_ORDER_ID.getCode(), origInstOrder.getInstOrderId().toString());
            return applyInstProcess(channelApiContext, refundOrder);
        } catch (Exception e) {
            log.error("退款异常", e);
            throw new BizException(e);
        }
    }

}
