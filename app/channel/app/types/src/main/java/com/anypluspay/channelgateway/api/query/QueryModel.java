package com.anypluspay.channelgateway.api.query;

import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.commons.lang.std.MoneyDeserializer;
import com.anypluspay.commons.lang.std.MoneySerializer;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 查询模型
 *
 * @author wxj
 * 2025/6/6
 */
@Data
public class QueryModel extends NormalContent {

    /**
     * 原支付单机构请求号，在退款查询时可能会用到
     */
    private String origInstRequestNo;

    /**
     * 原支付单机构响应号，在退款查询时可能会用到
     */
    private String OrigInstResponseNo;

    /**
     * 金额
     */
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money amount;

}
