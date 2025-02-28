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
 * 普通支付单
 * </p>
 *
 * @author wxj
 * @since 2025-02-28
 */
@TableName("tp_general_pay_order")
public class GeneralPayOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付单号
     */
    @TableId(value = "order_id", type = IdType.NONE)
    private String orderId;

    /**
     * 支付总单ID
     */
    private String paymentId;

    /**
     * 请求ID
     */
    private String requestId;

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
     * 订单状态
     */
    private String orderStatus;

    /**
     * 扩展信息
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
        return "GeneralPayOrderDO{" +
        "orderId = " + orderId +
        ", paymentId = " + paymentId +
        ", requestId = " + requestId +
        ", memberId = " + memberId +
        ", amount = " + amount +
        ", currencyCode = " + currencyCode +
        ", orderStatus = " + orderStatus +
        ", extension = " + extension +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
