package com.anypluspay.payment.facade.deposit;

import com.anypluspay.payment.facade.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 充值服务
 * @author wxj
 * 2025/5/14
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "DepositFacade")
public interface DepositFacade {

    String PREFIX = "/deposit";

    /**
     * 申请
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/apply")
    DepositResponse apply(@RequestBody DepositRequest request);

}
