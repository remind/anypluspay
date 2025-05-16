package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.payment.facade.response.BaseResponse;
import lombok.Data;

/**
 * 提现响应
 *
 * @author wxj
 * 2025/5/15
 */
@Data
public class WithdrawResponse extends BaseResponse {

    /**
     * 状态
     */
    private String status;
}