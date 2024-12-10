package com.anypluspay.channelgateway.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.channelgateway.request.GatewayOrder;

/**
 * @author wxj
 * 2024/12/10
 */
public abstract class AbstractLocalBankGateway {

    protected boolean isTest(GatewayOrder gatewayOrder) {
        return StrUtil.isNotBlank(gatewayOrder.getExtValue(ExtKey.TEST_FLAG));
    }

    protected TestFlag getTestFlag(GatewayOrder gatewayOrder) {
        if (isTest(gatewayOrder)) {
            return JSONUtil.toBean(gatewayOrder.getExtValue(ExtKey.TEST_FLAG), TestFlag.class);
        }
        return null;
    }
}
