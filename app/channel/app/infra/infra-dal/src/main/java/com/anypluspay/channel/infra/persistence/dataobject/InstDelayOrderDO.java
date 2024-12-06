package com.anypluspay.channel.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 机构延迟订单
 * </p>
 *
 * @author wxj
 * @since 2024-08-22
 */
@TableName("ti_inst_delay_order")
public class InstDelayOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构订单号
     */
    @TableId(value = "inst_order_id", type = IdType.NONE)
    private String instOrderId;

    /**
     * 状态
     */
    private String status;

    /**
     * 处理次数
     */
    private Integer count;

    /**
     * 预约处理时间
     */
    private LocalDateTime bookProcessTime;

    /**
     * 最后处理时间
     */
    private LocalDateTime lastProcessTime;

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

    public String getInstOrderId() {
        return instOrderId;
    }

    public void setInstOrderId(String instOrderId) {
        this.instOrderId = instOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDateTime getBookProcessTime() {
        return bookProcessTime;
    }

    public void setBookProcessTime(LocalDateTime bookProcessTime) {
        this.bookProcessTime = bookProcessTime;
    }

    public LocalDateTime getLastProcessTime() {
        return lastProcessTime;
    }

    public void setLastProcessTime(LocalDateTime lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
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
        return "InstDelayOrderDO{" +
        "instOrderId = " + instOrderId +
        ", status = " + status +
        ", count = " + count +
        ", bookProcessTime = " + bookProcessTime +
        ", lastProcessTime = " + lastProcessTime +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
