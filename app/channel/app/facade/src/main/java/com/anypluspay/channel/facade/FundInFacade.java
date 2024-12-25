package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 入款处理
 * @author wxj
 * 2024/9/24
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "FundInFacade")
public interface FundInFacade {

    String PREFIX = "/fund-in";

    /**
     * 申请入款
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/apply")
    FundResult apply(@RequestBody FundInRequest request);
}
