package com.anypluspay.channel.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * API结果码
 * </p>
 *
 * @author wxj
 * @since 2024-12-01
 */
@TableName("tc_api_result_code")
public class ApiResultCodeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 接口类型
     */
    private String apiType;

    /**
     * 机构API结果码
     */
    private String instApiCode;

    /**
     * 机构API子结果码
     */
    private String instApiSubCode;

    /**
     * 机构API消息
     */
    private String instApiMessage;

    /**
     * 统一结果码
     */
    private String unityCode;

    /**
     * 机构订单结果状态
     */
    private String resultStatus;

    /**
     * 是否映射
     */
    private Boolean useMapping;

    /**
     * 是否重路由
     */
    private Boolean reRouteEnable;

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

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getInstApiCode() {
        return instApiCode;
    }

    public void setInstApiCode(String instApiCode) {
        this.instApiCode = instApiCode;
    }

    public String getInstApiSubCode() {
        return instApiSubCode;
    }

    public void setInstApiSubCode(String instApiSubCode) {
        this.instApiSubCode = instApiSubCode;
    }

    public String getInstApiMessage() {
        return instApiMessage;
    }

    public void setInstApiMessage(String instApiMessage) {
        this.instApiMessage = instApiMessage;
    }

    public String getUnityCode() {
        return unityCode;
    }

    public void setUnityCode(String unityCode) {
        this.unityCode = unityCode;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Boolean getUseMapping() {
        return useMapping;
    }

    public void setUseMapping(Boolean useMapping) {
        this.useMapping = useMapping;
    }

    public Boolean getReRouteEnable() {
        return reRouteEnable;
    }

    public void setReRouteEnable(Boolean reRouteEnable) {
        this.reRouteEnable = reRouteEnable;
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
        return "ApiResultCodeDO{" +
        "id = " + id +
        ", channelCode = " + channelCode +
        ", apiType = " + apiType +
        ", instApiCode = " + instApiCode +
        ", instApiSubCode = " + instApiSubCode +
        ", instApiMessage = " + instApiMessage +
        ", unityCode = " + unityCode +
        ", resultStatus = " + resultStatus +
        ", useMapping = " + useMapping +
        ", reRouteEnable = " + reRouteEnable +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
