package com.anypluspay.admin.channel.model.request;

import com.anypluspay.commons.validator.UpdateValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author wxj
 * 2024/11/28
 */
@Data
public class ChannelSupportInstRequest {

    /**
     * 主键
     */
    @NotNull(message = "ID不能为空", groups = UpdateValidate.class)
    private Long id;

    /**
     * 渠道编码
     */
    @NotNull(message = "渠道编码不能为空")
    @Length(min = 3, max = 16, message = "渠道编码长度为3-16位")
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
}
