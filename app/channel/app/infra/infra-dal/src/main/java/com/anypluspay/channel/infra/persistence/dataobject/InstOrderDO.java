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
 * @since 2024-12-19
 */
@TableName("ti_inst_order")
public class InstOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构订单号
     */
    @TableId(value = "inst_order_id", type = IdType.AUTO)
    private Long instOrderId;

    /**
     * 业务订单号
     */
    private String bizOrderId;

    /**
     * 提交时间类型
     */
    private String submitTimeType;

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
     * 提交时间
     */
    private LocalDateTime submitTime;

    /**
     * 预约提交时间
     */
    private LocalDateTime bookSubmitTime;

    /**
     * 下次补单时间
     */
    private LocalDateTime nextRetryTime;

    /**
     * 补单次数
     */
    private Integer retryTimes;

    /**
     * 任务状态
     */
    private String taskStatus;

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

    public Long getInstOrderId() {
        return instOrderId;
    }

    public void setInstOrderId(Long instOrderId) {
        this.instOrderId = instOrderId;
    }

    public String getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(String bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public String getSubmitTimeType() {
        return submitTimeType;
    }

    public void setSubmitTimeType(String submitTimeType) {
        this.submitTimeType = submitTimeType;
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

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public LocalDateTime getBookSubmitTime() {
        return bookSubmitTime;
    }

    public void setBookSubmitTime(LocalDateTime bookSubmitTime) {
        this.bookSubmitTime = bookSubmitTime;
    }

    public LocalDateTime getNextRetryTime() {
        return nextRetryTime;
    }

    public void setNextRetryTime(LocalDateTime nextRetryTime) {
        this.nextRetryTime = nextRetryTime;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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
        ", submitTimeType = " + submitTimeType +
        ", instRequestNo = " + instRequestNo +
        ", instResponseNo = " + instResponseNo +
        ", fundChannelCode = " + fundChannelCode +
        ", apiType = " + apiType +
        ", status = " + status +
        ", requestExtra = " + requestExtra +
        ", responseExtra = " + responseExtra +
        ", submitTime = " + submitTime +
        ", bookSubmitTime = " + bookSubmitTime +
        ", nextRetryTime = " + nextRetryTime +
        ", retryTimes = " + retryTimes +
        ", taskStatus = " + taskStatus +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
