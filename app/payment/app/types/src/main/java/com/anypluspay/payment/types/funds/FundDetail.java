package com.anypluspay.payment.types.funds;

import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.paymethod.PayModel;
import lombok.Data;

/**
 * 资金明细
 * @author wxj
 * 2024/1/15
 */
@Data
public class FundDetail extends Entity  {

    /**
     * 支付总单ID
     */
    private String paymentId;

    /**
     * 支付指令ID，来自支付、退款指令
     */
    private String payProcessId;

    /**
     * 详情ID
     */
    private String detailId;

    /**
     * 关联ID，退款时关联其支付时的详情ID
     */
    private String relationId;

    /**
     * 资产所有人ID
     */
    private String memberId;

    /**
     * 所属方
     */
    private BelongTo belongTo;

    /**
     * 资金动作
     */
    private FundAction fundAction;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 资产信息
     */
    private AssetInfo assetInfo;

    /**
     * 支付模式
     */
    private PayModel payModel;

    /**
     * 支付参数
     */
    private Extension payParam;

    /**
     * 扩展信息
     */
    private Extension extension;
}
