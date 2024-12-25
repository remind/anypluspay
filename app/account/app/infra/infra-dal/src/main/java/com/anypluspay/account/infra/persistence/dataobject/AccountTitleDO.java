package com.anypluspay.account.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会计科目表
 * </p>
 *
 * @author wxj
 * @since 2024-12-24
 */
@TableName("t_account_title")
public class AccountTitleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 科目代码
     */
    @TableId(value = "code", type = IdType.NONE)
    private String code;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 科目级别
     */
    private Short level;

    /**
     * 父科目代码
     */
    private String parentCode;

    /**
     * 是否为叶子节点：1 是， 0 否
     */
    private String leaf;

    /**
     * 类型：1（资产类）；2（负债类）；3(所有者权益)；4（共同类）5(损益类)
     */
    private String type;

    /**
     * 余额方向：1:借 2:贷 0:双向
     */
    private String balanceDirection;

    /**
     * 状态：1（有效）；0（无效）
     */
    private String status;

    /**
     * 适用范围：1.内部科目;2,外部科目
     */
    private String range;

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

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(String balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
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
        return "AccountTitleDO{" +
        "code = " + code +
        ", name = " + name +
        ", level = " + level +
        ", parentCode = " + parentCode +
        ", leaf = " + leaf +
        ", type = " + type +
        ", balanceDirection = " + balanceDirection +
        ", status = " + status +
        ", range = " + range +
        ", memo = " + memo +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
