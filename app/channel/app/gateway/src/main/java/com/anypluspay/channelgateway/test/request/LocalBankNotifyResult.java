package com.anypluspay.channelgateway.test.request;

import lombok.Data;

/**
 * @author wxj
 * 2024/8/20
 */
@Data
public class LocalBankNotifyResult {

    private String instRequestNo;

    private String resultCode;

}
