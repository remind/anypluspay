package com.anypluspay.anypay.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 外部户
 * </p>
 *
 * @author wxj
 * @since 2024-12-26
 */
@TableName("t_outer_account")
public class OuterAccountDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账户号
     */
    @TableId(value = "account_no", type = IdType.NONE)
    private String accountNo;

    /**
     * 科目号
     */
    private String titleCode;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 会员号
     */
    private String memberId;

    /**
     * 禁止状态
     */
    private String denyStatus;

    /**
     * 账户属性 1:对私，2:对公
     */
    private String accountAttribute;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 当前余额方向 1:借，2:贷
     */
    private String currentBalanceDirection;

    /**
     * 账户余额方向 1:借，2:贷，0:双向
     */
    private String balanceDirection;

    /**
     * 货币类型
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDenyStatus() {
        return denyStatus;
    }

    public void setDenyStatus(String denyStatus) {
        this.denyStatus = denyStatus;
    }

    public String getAccountAttribute() {
        return accountAttribute;
    }

    public void setAccountAttribute(String accountAttribute) {
        this.accountAttribute = accountAttribute;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrentBalanceDirection() {
        return currentBalanceDirection;
    }

    public void setCurrentBalanceDirection(String currentBalanceDirection) {
        this.currentBalanceDirection = currentBalanceDirection;
    }

    public String getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(String balanceDirection) {
        this.balanceDirection = balanceDirection;
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
        return "OuterAccountDO{" +
        "accountNo = " + accountNo +
        ", titleCode = " + titleCode +
        ", accountName = " + accountName +
        ", memberId = " + memberId +
        ", denyStatus = " + denyStatus +
        ", accountAttribute = " + accountAttribute +
        ", accountType = " + accountType +
        ", currentBalanceDirection = " + currentBalanceDirection +
        ", balanceDirection = " + balanceDirection +
        ", currencyCode = " + currencyCode +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
