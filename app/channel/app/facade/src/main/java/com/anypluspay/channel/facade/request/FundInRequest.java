package com.anypluspay.channel.facade.request;

import com.anypluspay.commons.lang.std.MoneyDeserializer;
import com.anypluspay.commons.lang.std.MoneySerializer;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2024/9/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FundInRequest extends FundRequest {

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 金额
     */
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money amount;

}
