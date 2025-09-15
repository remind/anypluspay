package com.anypluspay.payment.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * SQL执行记录
 * </p>
 *
 * @author wxj
 * @since 2025-07-11
 */
@TableName("t_sql_records")
public class SqlRecordsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联ID
     */
    private Long relationId;

    /**
     * 数据库名
     */
    private String dbName;

    /**
     * 是否为逻辑库
     */
    private Boolean isLogic;

    /**
     * 数据源ID
     */
    private Long dataSourceId;

    /**
     * 执行的SQL
     */
    private String sql;

    /**
     * 状态
     */
    private String state;

    /**
     * 执行时间
     */
    private LocalDateTime gmtTime;

    /**
     * 影响行
     */
    private Integer affectRow;

    /**
     * 耗时
     */
    private Integer duration;

    /**
     * 错误信息
     */
    private String errorMsg;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Boolean getIsLogic() {
        return isLogic;
    }

    public void setIsLogic(Boolean isLogic) {
        this.isLogic = isLogic;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getGmtTime() {
        return gmtTime;
    }

    public void setGmtTime(LocalDateTime gmtTime) {
        this.gmtTime = gmtTime;
    }

    public Integer getAffectRow() {
        return affectRow;
    }

    public void setAffectRow(Integer affectRow) {
        this.affectRow = affectRow;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
        return "SqlRecordsDO{" +
        "id = " + id +
        ", relationId = " + relationId +
        ", dbName = " + dbName +
        ", isLogic = " + isLogic +
        ", dataSourceId = " + dataSourceId +
        ", sql = " + sql +
        ", state = " + state +
        ", gmtTime = " + gmtTime +
        ", affectRow = " + affectRow +
        ", duration = " + duration +
        ", errorMsg = " + errorMsg +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
