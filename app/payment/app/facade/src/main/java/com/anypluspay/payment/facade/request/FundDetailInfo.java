package com.anypluspay.payment.facade.request;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 资金信息
 * @author remind
 * 2023年07月14日 20:31
 */
@Data
public class FundDetailInfo {

    /**
     * 资产用户ID
     */
    private String memberId;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 资产类型编码
     */
    private String assetTypeCode;

    /**
     * 资产信息json字符串
     */
    private String assetJsonStr;

}
