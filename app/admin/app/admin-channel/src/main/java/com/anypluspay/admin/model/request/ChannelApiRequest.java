package com.anypluspay.admin.model.request;

import com.anypluspay.admin.model.UpdateValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 渠道API请求参数
 * @author wxj
 * 2024/11/12
 */
@Data
public class ChannelApiRequest {

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
     * 类型
     */
    @NotNull(message = "类型不能为空")
    private String type;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 地址
     */
    private String address;

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 扩展信息
     */
    private String extra;

    /**
     * 备注
     */
    private String memo;
}
