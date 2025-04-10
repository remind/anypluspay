package com.anypluspay.channel.facade.fund.builder;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.facade.result.ChannelResult;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.result.CsResultCode;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2025/4/10
 */
@Component
public class FundResultBuilder {

    public FundResult buildFundInResult(BaseBizOrder bizOrder, InstOrder instOrder, InstCommandOrder instCommandOrder) {
        FundResult fundResult = new FundResult();
        fundResult.setInstRequestNo(instOrder.getInstRequestNo());
        fundResult.setInstResponseNo(instOrder.getInstResponseNo());
        fundResult.setResponseExt(instOrder.getResponseExt());
        fundResult.setNeedClearing(true);
        fundResult.setClearingAccountNo("40010010011560001");
        fillChannelResultCommon(fundResult, bizOrder, instOrder, instCommandOrder);
        return fundResult;
    }

    private void fillChannelResultCommon(ChannelResult result, BaseBizOrder bizOrder, InstOrder instOrder, InstCommandOrder instCommandOrder) {
        result.setRequestId(bizOrder.getRequestId());
        result.setOrderId(bizOrder.getOrderId());
        result.setFundChannelCode(instOrder.getFundChannelCode());
        if (instOrder.getStatus() == InstOrderStatus.INIT) {
            result.setUnityCode(CsResultCode.WAIT_SUBMIT_INST.getCode());
            result.setUnityMessage(CsResultCode.WAIT_SUBMIT_INST.getMessage());
        } else {
            result.setUnityCode(StrUtil.firstNonBlank(instCommandOrder.getUnityCode(), instCommandOrder.getApiCode()));
            result.setUnityMessage(StrUtil.firstNonBlank(instCommandOrder.getUnityMessage(), instCommandOrder.getApiMessage()));
        }
        result.setStatus(bizOrder.getStatus());
    }
}
