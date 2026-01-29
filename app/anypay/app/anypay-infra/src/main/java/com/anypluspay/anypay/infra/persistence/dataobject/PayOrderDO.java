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
 * 支付单
 * </p>
 *
 * @author wxj
 * @since 2026-01-29
 */
@TableName("t_pay_order")
public class PayOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付ID
     */
    @TableId(value = "pay_id", type = IdType.NONE)
    private String payId;

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 订单金额
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
     * 渠道编码
     */
    private String channelCode;

    /**
     * 渠道请求号
     */
    private String channelRequestNo;

    /**
     * 渠道响应号
     */
    private String channelResponseNo;

    /**
     * 渠道响应参数
     */
    private String channelParam;

    /**
     * 支付参数
     */
    private String payParam;

    /**
     * 结果码
     */
    private String resultCode;

    /**
     * 结果信息
     */
    private String resultMsg;

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

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
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

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelRequestNo() {
        return channelRequestNo;
    }

    public void setChannelRequestNo(String channelRequestNo) {
        this.channelRequestNo = channelRequestNo;
    }

    public String getChannelResponseNo() {
        return channelResponseNo;
    }

    public void setChannelResponseNo(String channelResponseNo) {
        this.channelResponseNo = channelResponseNo;
    }

    public String getChannelParam() {
        return channelParam;
    }

    public void setChannelParam(String channelParam) {
        this.channelParam = channelParam;
    }

    public String getPayParam() {
        return payParam;
    }

    public void setPayParam(String payParam) {
        this.payParam = payParam;
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
        return "PayOrderDO{" +
        "payId = " + payId +
        ", tradeId = " + tradeId +
        ", payMethod = " + payMethod +
        ", amount = " + amount +
        ", currency = " + currency +
        ", status = " + status +
        ", channelCode = " + channelCode +
        ", channelRequestNo = " + channelRequestNo +
        ", channelResponseNo = " + channelResponseNo +
        ", channelParam = " + channelParam +
        ", payParam = " + payParam +
        ", resultCode = " + resultCode +
        ", resultMsg = " + resultMsg +
        ", extension = " + extension +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
