package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.payment.facade.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 提现接口
 *
 * @author wxj
 * 2025/5/15
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "WithdrawFacade")
public interface WithdrawFacade {

    String PREFIX = "/withdraw";

    /**
     * 申请
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/apply")
    WithdrawResponse apply(@RequestBody WithdrawRequest request);
}
