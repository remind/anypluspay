package com.anypluspay.channel.domain.channel.fund;


import com.anypluspay.channel.domain.channel.BaseChannel;
import com.anypluspay.channel.types.enums.RequestType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 资金渠道
 *
 * @author wxj
 * 2024/6/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FundChannel extends BaseChannel {

    /**
     * 请求类型
     */
    private RequestType requestType;

    /**
     * 支持的支付方式
     */
    private List<String> payMethods;

}
