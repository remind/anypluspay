package com.anypluspay.channel.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 渠道服务-资金流入订单
 * </p>
 *
 * @author wxj
 * @since 2025-04-07
 */
@TableName("tb_fund_in_order")
public class FundInOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道订单号
     */
    @TableId(value = "order_id", type = IdType.NONE)
    private String orderId;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currencyCode;

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

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    public String getPayInst() {
        return payInst;
    }

    public void setPayInst(String payInst) {
        this.payInst = payInst;
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
        return "FundInOrderDO{" +
        "orderId = " + orderId +
        ", payModel = " + payModel +
        ", payInst = " + payInst +
        ", amount = " + amount +
        ", currencyCode = " + currencyCode +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
