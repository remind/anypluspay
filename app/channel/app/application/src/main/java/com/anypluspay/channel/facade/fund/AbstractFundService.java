package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.channel.api.service.ChannelApiDomainService;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.facade.AbstractChannelService;
import com.anypluspay.channel.facade.fund.builder.FundOrderBuilder;
import com.anypluspay.channel.facade.result.ChannelResult;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.GlobalResultCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 资金类处理
 *
 * @author wxj
 * 2024/7/12
 */
public abstract class AbstractFundService extends AbstractChannelService {

    @Autowired
    protected InstOrderRepository instOrderRepository;

    @Autowired
    protected BizOrderRepository bizOrderRepository;

    @Autowired
    protected FundOrderBuilder fundOrderBuilder;

    @Autowired
    protected ChannelApiDomainService channelApiDomainService;

    protected FundResult applyInstProcess(ChannelApiContext channelApiContext, BaseBizOrder bizOrder) {
        return (FundResult) super.applyInstProcess(channelApiContext, bizOrder);
    }

    protected FundResult applyInstProcess(BaseBizOrder bizOrder, ChannelApiType apiType) {
        return (FundResult) super.applyInstProcess(bizOrder, apiType);
    }

    @Override
    protected ChannelResult buildChannelResult(BaseBizOrder bizOrder, InstOrder instOrder, InstCommandOrder instCommandOrder) {
        FundResult fundResult = new FundResult();
        fundResult.setInstRequestNo(instOrder.getInstRequestNo());
        fundResult.setInstResponseNo(instOrder.getInstResponseNo());
        fundResult.setResponseExt(instOrder.getResponseExt());
        return fundResult;
    }

}
