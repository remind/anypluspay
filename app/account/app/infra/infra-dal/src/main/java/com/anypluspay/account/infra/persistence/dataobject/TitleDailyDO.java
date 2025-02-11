package com.anypluspay.account.infra.persistence.dataobject;

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
 * 科目日汇总
 * </p>
 *
 * @author wxj
 * @since 2025-02-11
 */
@TableName("t_title_daily")
public class TitleDailyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日期
     */
    private String accountDate;

    /**
     * 科目编号
     */
    private String titleCode;

    /**
     * 余额方向
     */
    private String balanceDirection;

    /**
     * 借记金额
     */
    private BigDecimal debitAmount;

    /**
     * 贷记金额
     */
    private BigDecimal creditAmount;

    /**
     * 借记次数
     */
    private Long debitCount;

    /**
     * 贷记次数
     */
    private Long creditCount;

    /**
     * 借记余额
     */
    private BigDecimal debitBalance;

    /**
     * 贷记余额
     */
    private BigDecimal creditBalance;

    /**
     * 币种代码
     */
    private String currencyCode;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 备注字段
     */
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(String balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Long getDebitCount() {
        return debitCount;
    }

    public void setDebitCount(Long debitCount) {
        this.debitCount = debitCount;
    }

    public Long getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Long creditCount) {
        this.creditCount = creditCount;
    }

    public BigDecimal getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(BigDecimal debitBalance) {
        this.debitBalance = debitBalance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "TitleDailyDO{" +
        "id = " + id +
        ", accountDate = " + accountDate +
        ", titleCode = " + titleCode +
        ", balanceDirection = " + balanceDirection +
        ", debitAmount = " + debitAmount +
        ", creditAmount = " + creditAmount +
        ", debitCount = " + debitCount +
        ", creditCount = " + creditCount +
        ", debitBalance = " + debitBalance +
        ", creditBalance = " + creditBalance +
        ", currencyCode = " + currencyCode +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        ", memo = " + memo +
        "}";
    }
}
