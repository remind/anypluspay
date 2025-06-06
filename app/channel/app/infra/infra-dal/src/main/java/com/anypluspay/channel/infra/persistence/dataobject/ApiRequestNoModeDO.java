package com.anypluspay.channel.infra.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 渠道API请求单号模式
 * </p>
 *
 * @author wxj
 * @since 2024-09-18
 */
@TableName("tc_api_request_no_mode")
public class ApiRequestNoModeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "code", type = IdType.NONE)
    private String code;

    /**
     * 生成模式
     */
    private String genPattern;

    /**
     * 序列名称
     */
    private String seqName;

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

    public String getGenPattern() {
        return genPattern;
    }

    public void setGenPattern(String genPattern) {
        this.genPattern = genPattern;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
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
        return "ApiRequestNoModeDO{" +
        "code = " + code +
        ", genPattern = " + genPattern +
        ", seqName = " + seqName +
        ", memo = " + memo +
        ", gmtCreate = " + gmtCreate +
        ", gmtModified = " + gmtModified +
        "}";
    }
}
