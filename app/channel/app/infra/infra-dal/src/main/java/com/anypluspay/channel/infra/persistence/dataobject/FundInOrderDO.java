package com.anypluspay.channel.infra.persistence.dataobject;

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
 * 渠道服务-资金流入订单
 * </p>
 *
 * @author wxj
 * @since 2024-11-12
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
     * 支付方式
     */
    private String payMethod;

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
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 终端类型
     */
    private String terminalType;

    /**
     * 终端信息
     */
    private String terminal;

    /**
     * 路由信息
     */
    private String routeExtra;

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

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getRouteExtra() {
        return routeExtra;
    }

    public void setRouteExtra(String routeExtra) {
        this.routeExtra = routeExtra;
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
        ", payMethod = " + payMethod +
        ", payInst = " + payInst +
        ", amount = " + amount +
        ", currencyCode = " + currencyCode +
        ", goodsDesc = " + goodsDesc +
        ", terminalType = " + terminalType +
        ", terminal = " + terminal +
        ", routeExtra = " + routeExtra +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
