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
 * 机构指令订单
 * </p>
 *
 * @author wxj
 * @since 2025-04-07
 */
@TableName("ti_inst_command_order")
public class InstCommandOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 指令ID
     */
    @TableId(value = "command_id", type = IdType.AUTO)
    private Long commandId;

    /**
     * 机构订单号
     */
    private Long instOrderId;

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
     * 统一结果码
     */
    private String unityCode;

    /**
     * 统一结果消息
     */
    private String unityMessage;

    /**
     * 渠道API结果码
     */
    private String apiCode;

    /**
     * 渠道API子结果码
     */
    private String apiSubCode;

    /**
     * 渠道API消息
     */
    private String apiMessage;

    /**
     * 扩展信息
     */
    private String extension;

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

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public Long getInstOrderId() {
        return instOrderId;
    }

    public void setInstOrderId(Long instOrderId) {
        this.instOrderId = instOrderId;
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

    public String getUnityCode() {
        return unityCode;
    }

    public void setUnityCode(String unityCode) {
        this.unityCode = unityCode;
    }

    public String getUnityMessage() {
        return unityMessage;
    }

    public void setUnityMessage(String unityMessage) {
        this.unityMessage = unityMessage;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getApiSubCode() {
        return apiSubCode;
    }

    public void setApiSubCode(String apiSubCode) {
        this.apiSubCode = apiSubCode;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
        return "InstCommandOrderDO{" +
        "commandId = " + commandId +
        ", instOrderId = " + instOrderId +
        ", fundChannelCode = " + fundChannelCode +
        ", apiType = " + apiType +
        ", status = " + status +
        ", unityCode = " + unityCode +
        ", unityMessage = " + unityMessage +
        ", apiCode = " + apiCode +
        ", apiSubCode = " + apiSubCode +
        ", apiMessage = " + apiMessage +
        ", extension = " + extension +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
