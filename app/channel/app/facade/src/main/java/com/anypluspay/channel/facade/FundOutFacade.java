package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.result.FundResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 出款
 * @author wxj
 * 2024/7/18
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "FundOutFacade")
public interface FundOutFacade {

    String PREFIX = "/fund-out";

    /**
     * 出款
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/apply")
    FundResult apply(@RequestBody FundOutRequest request);
}
