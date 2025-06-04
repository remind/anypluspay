package com.anypluspay.channel.application.institution.gateway.advice;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.application.institution.CombineCallbackUrlService;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channelgateway.api.sign.SignNormalContent;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.types.channel.ChannelApiType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/9
 */
@Service
public class SignRequestAdvice implements GatewayRequestAdvice<SignNormalContent, SignResult> {

    @Autowired
    private CombineCallbackUrlService combineCallbackUrlService;

    @Override
    public void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, SignNormalContent requestContent) {
        if (orderContext.getBizOrder() instanceof FundInOrder fundInOrder) {
            requestContent.setGoodsSubject(fundInOrder.getExtension().get(ChannelExtKey.GOODS_SUBJECT.getCode()));
            requestContent.setGoodsDesc(fundInOrder.getExtension().get(ChannelExtKey.GOODS_DESC.getCode()));
            requestContent.setReturnPageUrl(combineCallbackUrlService.getReturnPageUrl(channelApiContext));
        }
    }

    @Override
    public void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, SignResult result) {
        InstOrder instOrder = orderContext.getInstOrder();
        instOrder.setInstResponseNo(result.getInstResponseNo());
        instOrder.getResponseExt().add(ChannelExtKey.INST_REDIRECTION_DATA.getCode(), JSONUtil.toJsonStr(result.getRedirectionData()));
    }

    @Override
    public ChannelApiType supportApiType() {
        return ChannelApiType.SIGN;
    }

}
