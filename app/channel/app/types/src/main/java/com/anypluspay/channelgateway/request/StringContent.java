package com.anypluspay.channelgateway.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wxj
 * 2024/9/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringContent extends RequestContent {

    /**
     * 请求报文
     */
    private String requestBody;
}
