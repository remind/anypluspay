package com.anypluspay.channelgateway.api.verify;

import com.anypluspay.channelgateway.request.RequestContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 通知请求模型
 * @author wxj
 * 2025/6/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyModel extends RequestContent {

    /**
     * 回调类型
     */
    private String callbackType;

    /**
     * 请求报文
     */
    private String requestBody;

}
