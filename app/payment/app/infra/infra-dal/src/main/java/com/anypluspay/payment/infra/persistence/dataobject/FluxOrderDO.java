package com.anypluspay.payment.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 交换单
 * </p>
 *
 * @author wxj
 * @since 2025-06-06
 */
@TableName("tf_flux_order")
public class FluxOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交换单ID
     */
    @TableId(value = "flux_order_id", type = IdType.NONE)
    private String fluxOrderId;

    /**
     * 支付ID
     */
    private String orderId;

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 交换状态
     */
    private String status;

    /**
     * 关联订单ID
     */
    private String relationId;

    /**
     * 支付指令类型
     */
    private String payType;

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

    public String getFluxOrderId() {
        return fluxOrderId;
    }

    public void setFluxOrderId(String fluxOrderId) {
        this.fluxOrderId = fluxOrderId;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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
        return "FluxOrderDO{" +
        "fluxOrderId = " + fluxOrderId +
        ", orderId = " + orderId +
        ", tradeId = " + tradeId +
        ", status = " + status +
        ", relationId = " + relationId +
        ", payType = " + payType +
        ", resultCode = " + resultCode +
        ", resultMsg = " + resultMsg +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
