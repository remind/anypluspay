package com.anypluspay.channel.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 渠道支持机构
 * </p>
 *
 * @author wxj
 * @since 2024-11-28
 */
@TableName("tc_channel_support_inst")
public class ChannelSupportInstDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 目标机构编码
     */
    private String targetInstCode;

    /**
     * 卡类型
     */
    private String cardType;

    /**
     * 扩展条件
     */
    private String extra;

    /**
     * 单笔限额
     */
    private String perAmountRange;

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

    public String getTargetInstCode() {
        return targetInstCode;
    }

    public void setTargetInstCode(String targetInstCode) {
        this.targetInstCode = targetInstCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPerAmountRange() {
        return perAmountRange;
    }

    public void setPerAmountRange(String perAmountRange) {
        this.perAmountRange = perAmountRange;
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
        return "ChannelSupportInstDO{" +
        "id = " + id +
        ", channelCode = " + channelCode +
        ", targetInstCode = " + targetInstCode +
        ", cardType = " + cardType +
        ", extra = " + extra +
        ", perMountRange = " + perAmountRange +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
