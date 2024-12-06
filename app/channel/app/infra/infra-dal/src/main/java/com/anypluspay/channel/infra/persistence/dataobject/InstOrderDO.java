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
 * 机构订单
 * </p>
 *
 * @author wxj
 * @since 2024-08-25
 */
@TableName("ti_inst_order")
public class InstOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构订单号
     */
    @TableId(value = "inst_order_id", type = IdType.NONE)
    private String instOrderId;

    /**
     * 业务订单号
     */
    private String bizOrderId;

    /**
     * 处理时间类型
     */
    private String processTimeType;

    /**
     * 机构请求单号
     */
    private String instRequestNo;

    /**
     * 机构响应单号
     */
    private String instResponseNo;

    /**
     * 渠道编码
     */
    private String fundChannelCode;

    /**
     * 接口类型
     */
    private String apiType;

    /**
     * 状态
     */
    private String status;

    /**
     * 请求扩展信息
     */
    private String requestExtra;

    /**
     * 响应扩展信息
     */
    private String responseExtra;

    /**
     * 处理时间
     */
    private LocalDateTime processTime;

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

    public String getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(String bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public String getProcessTimeType() {
        return processTimeType;
    }

    public void setProcessTimeType(String processTimeType) {
        this.processTimeType = processTimeType;
    }

    public String getInstRequestNo() {
        return instRequestNo;
    }

    public void setInstRequestNo(String instRequestNo) {
        this.instRequestNo = instRequestNo;
    }

    public String getInstResponseNo() {
        return instResponseNo;
    }

    public void setInstResponseNo(String instResponseNo) {
        this.instResponseNo = instResponseNo;
    }

    public String getFundChannelCode() {
        return fundChannelCode;
    }

    public void setFundChannelCode(String fundChannelCode) {
        this.fundChannelCode = fundChannelCode;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestExtra() {
        return requestExtra;
    }

    public void setRequestExtra(String requestExtra) {
        this.requestExtra = requestExtra;
    }

    public String getResponseExtra() {
        return responseExtra;
    }

    public void setResponseExtra(String responseExtra) {
        this.responseExtra = responseExtra;
    }

    public LocalDateTime getProcessTime() {
        return processTime;
    }

    public void setProcessTime(LocalDateTime processTime) {
        this.processTime = processTime;
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
        return "InstOrderDO{" +
        "instOrderId = " + instOrderId +
        ", bizOrderId = " + bizOrderId +
        ", processTimeType = " + processTimeType +
        ", instRequestNo = " + instRequestNo +
        ", instResponseNo = " + instResponseNo +
        ", fundChannelCode = " + fundChannelCode +
        ", apiType = " + apiType +
        ", status = " + status +
        ", requestExtra = " + requestExtra +
        ", responseExtra = " + responseExtra +
        ", processTime = " + processTime +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
