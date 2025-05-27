package com.anypluspay.payment.facade.request;

import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 资金信息
 *
 * @author remind
 * 2023年07月14日 20:31
 */
@Data
public class FundDetailInfo {

    /**
     * 资产用户ID
     */
    @Length(min = 6, max = 15, message = "会员ID长度为6-15")
    private String memberId;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Money amount;

    /**
     * 资产类型编码
     */
    @Length(min = 6, max = 15, message = "资产类型编码长度为6-15")
    private String assetTypeCode;

    /**
     * 资产信息json字符串
     */
    @Length(min = 6, max = 1024, message = "资产信息json字符串长度不得超过1024")
    private String assetJsonStr;

    /**
     * 支付模式
     */
    @Length(min = 6, max = 15, message = "支付模式长度为6-15")
    private String payModel;

    /**
     * 支付参数
     */
    @Length(max = 1024, message = "支付参数长度不得超过1024")
    private String payParam;

    /**
     * 扩展信息
     */
    @Length(max = 1024, message = "扩展信息长度不得超过1024")
    private String extension;

}
