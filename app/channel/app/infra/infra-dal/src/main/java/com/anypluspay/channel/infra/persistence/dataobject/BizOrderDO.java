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
 * 业务订单
 * </p>
 *
 * @author wxj
 * @since 2024-12-06
 */
@TableName("tb_biz_order")
public class BizOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道订单号
     */
    @TableId(value = "order_id", type = IdType.NONE)
    private String orderId;

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 请求根类型
     */
    private String requestRootType;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 状态
     */
    private String status;

    /**
     * 机构订单ID
     */
    private String instOrderId;

    /**
     * 扩展信息
     */
    private String extra;

    /**
     * 机构扩展信息
     */
    private String instExtra;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRequestRootType() {
        return requestRootType;
    }

    public void setRequestRootType(String requestRootType) {
        this.requestRootType = requestRootType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstOrderId() {
        return instOrderId;
    }

    public void setInstOrderId(String instOrderId) {
        this.instOrderId = instOrderId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getInstExtra() {
        return instExtra;
    }

    public void setInstExtra(String instExtra) {
        this.instExtra = instExtra;
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
        return "BizOrderDO{" +
        "orderId = " + orderId +
        ", requestId = " + requestId +
        ", memberId = " + memberId +
        ", requestRootType = " + requestRootType +
        ", requestType = " + requestType +
        ", status = " + status +
        ", instOrderId = " + instOrderId +
        ", extra = " + extra +
        ", instExtra = " + instExtra +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
