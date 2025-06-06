package com.anypluspay.channel.facade.control;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.control.ControlBizOrder;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.facade.AbstractChannelService;
import com.anypluspay.channel.facade.control.request.ControlRequest;
import com.anypluspay.channel.facade.result.ChannelResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.enums.RequestType;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/18
 */
@Service
public class ControlOrderServiceImpl extends AbstractChannelService implements ControlFacade {

    @Override
    public ChannelResult apply(ControlRequest controlRequest) {
        ControlBizOrder controlOrder = new ControlBizOrder(RequestType.DOWNLOAD_BILL);
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(controlOrder.getFundChannelCode(), getApiType(controlOrder.getRequestType()));
        return applyInstProcess(channelApiContext, controlOrder);
    }

    private ChannelApiType getApiType(RequestType requestType) {
        switch (requestType) {
            case DOWNLOAD_BILL:
                return ChannelApiType.DOWNLOAD_BILL;
            default:
                return null;
        }
    }

    @Override
    protected ChannelResult buildChannelResult(BaseBizOrder bizOrder, InstOrder instOrder, InstCommandOrder instCommandOrder) {
        ChannelResult result = new ChannelResult();
        fillChannelResultCommon(result, bizOrder, instOrder, instCommandOrder);
        return result;
    }
}
