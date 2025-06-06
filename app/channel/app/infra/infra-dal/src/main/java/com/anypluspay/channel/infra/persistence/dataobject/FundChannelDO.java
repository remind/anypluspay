package com.anypluspay.channel.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资金渠道
 * </p>
 *
 * @author wxj
 * @since 2025-04-11
 */
@TableName("tc_fund_channel")
public class FundChannelDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "code", type = IdType.NONE)
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属机构代码
     */
    private String instCode;

    /**
     * 请求模式
     */
    private String requestType;

    /**
     * 支持的支付方式
     */
    private String payMethods;

    /**
     * 流入待清算账户
     */
    private String inClearingAccount;

    /**
     * 流出待清算账户
     */
    private String outClearingAccount;

    /**
     * 状态，是否可用
     */
    private Boolean enable;

    /**
     * 扩展信息
     */
    private String extra;

    /**
     * 备注
     */
    private String memo;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getPayMethods() {
        return payMethods;
    }

    public void setPayMethods(String payMethods) {
        this.payMethods = payMethods;
    }

    public String getInClearingAccount() {
        return inClearingAccount;
    }

    public void setInClearingAccount(String inClearingAccount) {
        this.inClearingAccount = inClearingAccount;
    }

    public String getOutClearingAccount() {
        return outClearingAccount;
    }

    public void setOutClearingAccount(String outClearingAccount) {
        this.outClearingAccount = outClearingAccount;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
        return "FundChannelDO{" +
        "code = " + code +
        ", name = " + name +
        ", instCode = " + instCode +
        ", requestType = " + requestType +
        ", payMethods = " + payMethods +
        ", inClearingAccount = " + inClearingAccount +
        ", outClearingAccount = " + outClearingAccount +
        ", enable = " + enable +
        ", extra = " + extra +
        ", memo = " + memo +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
