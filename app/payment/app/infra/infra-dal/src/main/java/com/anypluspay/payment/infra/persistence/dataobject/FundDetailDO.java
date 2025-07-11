package com.anypluspay.payment.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付单资金明细
 * </p>
 *
 * @author wxj
 * @since 2025-06-06
 */
@TableName("tp_fund_detail")
public class FundDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 明细ID
     */
    @TableId(value = "detail_id", type = IdType.NONE)
    private String detailId;

    /**
     * 支付ID
     */
    private String orderId;

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 关联ID，如退款关联的原ID
     */
    private String relationId;

    /**
     * 归属方
     */
    private String belongTo;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 资金动作
     */
    private String fundAction;

    /**
     * 资产类型
     */
    private String assetType;

    /**
     * 资产信息
     */
    private String assetInfo;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 交换参数
     */
    private String payParam;

    /**
     * 扩展字段
     */
    private String extension;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFundAction() {
        return fundAction;
    }

    public void setFundAction(String fundAction) {
        this.fundAction = fundAction;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetInfo() {
        return assetInfo;
    }

    public void setAssetInfo(String assetInfo) {
        this.assetInfo = assetInfo;
    }

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    public String getPayParam() {
        return payParam;
    }

    public void setPayParam(String payParam) {
        this.payParam = payParam;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "FundDetailDO{" +
        "detailId = " + detailId +
        ", orderId = " + orderId +
        ", tradeId = " + tradeId +
        ", relationId = " + relationId +
        ", belongTo = " + belongTo +
        ", memberId = " + memberId +
        ", amount = " + amount +
        ", currencyCode = " + currencyCode +
        ", fundAction = " + fundAction +
        ", assetType = " + assetType +
        ", assetInfo = " + assetInfo +
        ", payModel = " + payModel +
        ", payParam = " + payParam +
        ", extension = " + extension +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
