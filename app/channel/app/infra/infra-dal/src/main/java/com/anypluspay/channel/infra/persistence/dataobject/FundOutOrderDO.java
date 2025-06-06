package com.anypluspay.channel.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 资金流出订单
 * </p>
 *
 * @author wxj
 * @since 2024-12-19
 */
@TableName("tb_fund_out_order")
public class FundOutOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道订单号
     */
    @TableId(value = "order_id", type = IdType.NONE)
    private String orderId;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户类型：B（公司账户），C（个人账户）
     */
    private String accountType;

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

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
        return "FundOutOrderDO{" +
        "orderId = " + orderId +
        ", payMethod = " + payMethod +
        ", bankCard = " + bankCode +
        ", amount = " + amount +
        ", currencyCode = " + currencyCode +
        ", accountNo = " + accountNo +
        ", accountName = " + accountName +
        ", accountType = " + accountType +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
