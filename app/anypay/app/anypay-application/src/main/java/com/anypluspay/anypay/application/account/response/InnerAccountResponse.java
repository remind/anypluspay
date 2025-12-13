package com.anypluspay.anypay.application.account.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2025/1/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InnerAccountResponse extends AccountResponse {

    /**
     * 备注
     */
    private String memo;
}
