package com.anypluspay.channel.facade.manager;

import com.anypluspay.channel.facade.ApiConstants;
import com.anypluspay.channel.facade.manager.response.ChannelApiParamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 资金渠道管理
 *
 * @author wxj
 * 2025/6/4
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "FundChannelManagerFacade")
public interface FundChannelManagerFacade {
    String PREFIX = "/fund-channel-manager";

    /**
     * 获取渠道api参数
     *
     * @param paramId 参数id
     * @return
     */
    @GetMapping(PREFIX + "/get-channel-api-param")
    ChannelApiParamResponse getChannelApiParam(@RequestParam String paramId);
}
