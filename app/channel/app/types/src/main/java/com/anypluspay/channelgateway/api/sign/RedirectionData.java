package com.anypluspay.channelgateway.api.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 跳转参数
 * @author wxj
 * 2025/6/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectionData {

    /**
     * 跳转类型
     */
    private String type;

    /**
     * 跳转类型
     */
    private String content;
}
