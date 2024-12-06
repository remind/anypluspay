package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;

/**
 * 退款业务处理
 *
 * @author wxj
 * 2024/7/18
 */
public interface RefundFacade {

    /**
     * 退款申请
     *
     * @param request 退款订单
     * @return 订单结果
     */
    FundResult apply(RefundRequest request);

}
