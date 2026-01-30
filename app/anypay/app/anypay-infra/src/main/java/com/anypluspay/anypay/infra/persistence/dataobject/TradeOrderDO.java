package com.anypluspay.anypay.infra.persistence.dataobject;

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
 * 交易订单表
 * </p>
 *
 * @author wxj
 * @since 2026-01-30
 */
@TableName("t_trade_order")
public class TradeOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易ID
     */
    @TableId(value = "trade_id", type = IdType.NONE)
    private String tradeId;

    /**
     * 关联交易ID
     */
    private String relationTradeId;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 外部交易单号
     */
    private String outTradeNo;

    /**
     * 收款方ID
     */
    private String payeeId;

    /**
     * 收款方账户号
     */
    private String payeeAccountNo;

    /**
     * 付款方ID
     */
    private String payerId;

    /**
     * 标题
     */
    private String subject;

    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 状态
     */
    private String status;

    /**
     * 过期时间
     */
    private LocalDateTime gmtExpire;

    /**
     * 扩展信息
     */
    private String extension;

    /**
     * 跳转地址
     */
    private String returnUrl;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 通知时间
     */
    private LocalDateTime notifyTime;

    /**
     * S-成功，W-待通知
     */
    private String notifyStatus;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getRelationTradeId() {
        return relationTradeId;
    }

    public void setRelationTradeId(String relationTradeId) {
        this.relationTradeId = relationTradeId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getGmtExpire() {
        return gmtExpire;
    }

    public void setGmtExpire(LocalDateTime gmtExpire) {
        this.gmtExpire = gmtExpire;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public LocalDateTime getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(LocalDateTime notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
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
        return "TradeOrderDO{" +
        "tradeId = " + tradeId +
        ", relationTradeId = " + relationTradeId +
        ", tradeType = " + tradeType +
        ", outTradeNo = " + outTradeNo +
        ", payeeId = " + payeeId +
        ", payeeAccountNo = " + payeeAccountNo +
        ", payerId = " + payerId +
        ", subject = " + subject +
        ", body = " + body +
        ", amount = " + amount +
        ", currency = " + currency +
        ", status = " + status +
        ", gmtExpire = " + gmtExpire +
        ", extension = " + extension +
        ", returnUrl = " + returnUrl +
        ", notifyUrl = " + notifyUrl +
        ", notifyTime = " + notifyTime +
        ", notifyStatus = " + notifyStatus +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
