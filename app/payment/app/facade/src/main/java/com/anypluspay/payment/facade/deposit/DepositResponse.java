package com.anypluspay.payment.facade.deposit;

import com.anypluspay.commons.lang.BaseResult;
import lombok.Data;

/**
 * @author wxj
 * 2025/5/14
 */
@Data
public class DepositResponse extends BaseResult {

    /**
     * 充值ID
     */
    private String tradeId;

    /**
     * 状态
     */
    private String orderStatus;

    /**
     * 机构跳转参数
     */
    private String ird;
}
