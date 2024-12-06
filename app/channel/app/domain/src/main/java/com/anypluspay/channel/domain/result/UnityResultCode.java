package com.anypluspay.channel.domain.result;

import lombok.Data;

/**
 * 统一结果码
 * @author wxj
 * 2024/7/9
 */
@Data
public class UnityResultCode {

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息模板
     */
    private String template;

    /**
     * 备注
     */
    private String memo;
}
