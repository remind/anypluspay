package com.anypluspay.channelgateway.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.channelgateway.request.RequestContent;

/**
 * @author wxj
 * 2024/12/10
 */
public abstract class AbstractLocalBankGateway {

    protected boolean isTest(RequestContent requestContent) {
        return StrUtil.isNotBlank(requestContent.getExtValue(ChannelExtKey.TEST_FLAG));
    }

    protected TestFlag getTestFlag(RequestContent requestContent) {
        if (isTest(requestContent)) {
            return JSONUtil.toBean(requestContent.getExtValue(ChannelExtKey.TEST_FLAG), TestFlag.class);
        }
        return null;
    }
}
