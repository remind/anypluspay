package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 通知
 *
 * @author wxj
 * 2024/9/24
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "NotifyFacade")
public interface NotifyFacade {

    String PREFIX = "/notify";

    /**
     * 通知
     *
     * @param channelCode 渠道编码
     * @param channelApiType  通知类型
     * @param request         通知内容
     * @return
     */
    @PostMapping(PREFIX + "/apply/{channelCode}/{channelApiType}")
    FundResult notify(@PathVariable("channelCode") String channelCode, @PathVariable("channelApiType") ChannelApiType channelApiType, @RequestBody String request);
}
