package com.anypluspay.payment.facade.deposit;

import com.anypluspay.payment.facade.response.BaseResponse;
import lombok.Data;

/**
 * @author wxj
 * 2025/5/14
 */
@Data
public class DepositResponse extends BaseResponse {

    /**
     * 状态
     */
    private String status;
}
