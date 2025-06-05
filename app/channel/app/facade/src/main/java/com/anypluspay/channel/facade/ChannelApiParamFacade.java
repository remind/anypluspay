package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.response.ChannelApiParamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 渠道API参数
 *
 * @author wxj
 * 2025/6/4
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "ChannelApiParamFacade")
public interface ChannelApiParamFacade {
    String PREFIX = "/channel-api-param";

    /**
     * 获取渠道api参数
     *
     * @param id 参数id
     * @return
     */
    @GetMapping(PREFIX + "/get-channel-api-param")
    ChannelApiParamResponse getChannelApiParam(String id);
}
