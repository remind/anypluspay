package com.anypluspay.channel.domain.result;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import lombok.Data;

/**
 * @author wxj
 * 2024/7/10
 */
@Data
public class UnityResult {

    /**
     * 接口类型
     */
    private ChannelApiType apiType;

    /**
     * 是否匹配
     */
    private boolean isMatch;

    /**
     * 结果码
     */
    private String resultCode;

    /**
     * 结果描述
     */
    private String resultMessage;

    /**
     * 机构单状态
     */
    private InstOrderStatus status;

}
