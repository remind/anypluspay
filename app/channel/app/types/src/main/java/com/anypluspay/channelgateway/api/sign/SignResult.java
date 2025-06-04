package com.anypluspay.channelgateway.api.sign;

import com.anypluspay.channelgateway.result.GatewayResult;
import lombok.Data;

/**
 * @author wxj
 * 2024/9/15
 */
@Data
public class SignResult extends GatewayResult {

    /**
     * 机构跳转参数
     */
    private RedirectionData redirectionData;

}
