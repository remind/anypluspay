package com.anypluspay.channel.application.institution.validator;

import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.commons.exceptions.BizException;

/**
 * 机构结果验证
 *
 * @author wxj
 * 2024/8/20
 */
public class InstResultValidator {

    public static void validate(BaseBizOrder baseBizOrder, InstOrder instOrder, GatewayResult gatewayResult) {
        if (baseBizOrder instanceof FundInOrder) {
            if (gatewayResult.getRealAmount() != null && !gatewayResult.getRealAmount().equals(((FundInOrder) baseBizOrder).getAmount())) {
                throw new BizException("金额不一致");
            }
        }
    }
}
