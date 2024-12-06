package com.anypluspay.channelgateway.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxj
 * 2024/9/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringInfo extends RequestContent {

    /**
     * 请求报文
     */
    private String requestBody;
}
