package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 退款业务处理
 *
 * @author wxj
 * 2024/7/18
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "RefundFacade")
public interface RefundFacade {

    String PREFIX = "/refund";

    /**
     * 退款申请
     *
     * @param request 退款订单
     * @return 订单结果
     */
    @PostMapping(PREFIX + "/apply")
    FundResult apply(RefundRequest request);

}
