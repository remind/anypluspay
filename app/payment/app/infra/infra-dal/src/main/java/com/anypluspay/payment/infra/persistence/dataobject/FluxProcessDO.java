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
 * 交换指令
 * </p>
 *
 * @author wxj
 * @since 2025-05-20
 */
@TableName("t_flux_process")
public class FluxProcessDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交换指令ID
     */
    @TableId(value = "flux_process_id", type = IdType.NONE)
    private String fluxProcessId;

    /**
     * 上一条指令ID
     */
    private String preProcessId;

    /**
     * 交换单ID
     */
    private String fluxOrderId;

    /**
     * 支付指令ID
     */
    private String payProcessId;

    /**
     * 支付单ID
     */
    private String paymentId;

    /**
     * 类型
     */
    private String type;

    /**
     * 方向
     */
    private String direction;

    /**
     * 资金操作类型
     */
    private String fundAction;

    /**
     * 指令状态
     */
    private String status;

    /**
     * 关联订单ID
     */
    private String relationId;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 资金明细ID
     */
    private String fundDetailId;

    /**
     * 资产类型
     */
    private String assetType;

    /**
     * 资产信息
     */
    private String assetInfo;

    /**
     * 扩展字段
     */
    private String extension;

    /**
     * 结果码
     */
    private String resultCode;

    /**
     * 结果信息
     */
    private String resultMsg;

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

    public String getFluxProcessId() {
        return fluxProcessId;
    }

    public void setFluxProcessId(String fluxProcessId) {
        this.fluxProcessId = fluxProcessId;
    }

    public String getPreProcessId() {
        return preProcessId;
    }

    public void setPreProcessId(String preProcessId) {
        this.preProcessId = preProcessId;
    }

    public String getFluxOrderId() {
        return fluxOrderId;
    }

    public void setFluxOrderId(String fluxOrderId) {
        this.fluxOrderId = fluxOrderId;
    }

    public String getPayProcessId() {
        return payProcessId;
    }

    public void setPayProcessId(String payProcessId) {
        this.payProcessId = payProcessId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFundAction() {
        return fundAction;
    }

    public void setFundAction(String fundAction) {
        this.fundAction = fundAction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
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

    public String getFundDetailId() {
        return fundDetailId;
    }

    public void setFundDetailId(String fundDetailId) {
        this.fundDetailId = fundDetailId;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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
        return "FluxProcessDO{" +
        "fluxProcessId = " + fluxProcessId +
        ", prePrcessId = " + preProcessId +
        ", fluxOrderId = " + fluxOrderId +
        ", payProcessId = " + payProcessId +
        ", paymentId = " + paymentId +
        ", type = " + type +
        ", direction = " + direction +
        ", fundAction = " + fundAction +
        ", status = " + status +
        ", relationId = " + relationId +
        ", amount = " + amount +
        ", currencyCode = " + currencyCode +
        ", fundDetailId = " + fundDetailId +
        ", assetType = " + assetType +
        ", assetInfo = " + assetInfo +
        ", extension = " + extension +
        ", resultCode = " + resultCode +
        ", resultMsg = " + resultMsg +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
