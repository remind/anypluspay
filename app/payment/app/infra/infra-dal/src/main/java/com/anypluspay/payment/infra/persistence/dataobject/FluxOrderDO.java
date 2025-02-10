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
 * @since 2025-02-07
 */
@TableName("tp_flux_order")
public class FluxOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交换单ID
     */
    @TableId(value = "flux_order_id", type = IdType.NONE)
    private String fluxOrderId;

    /**
     * 支付单ID
     */
    private String payOrderId;

    /**
     * 支付总单ID
     */
    private String paymentId;

    /**
     * 交换状态
     */
    private String status;

    /**
     * 关联订单ID
     */
    private String relationId;

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

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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
        ", payOrderId = " + payOrderId +
        ", paymentId = " + paymentId +
        ", status = " + status +
        ", relationId = " + relationId +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
