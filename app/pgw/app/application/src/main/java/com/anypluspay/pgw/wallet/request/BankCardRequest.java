package com.anypluspay.pgw.wallet.request;

import com.anypluspay.commons.validator.UpdateValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wxj
 * 2025/7/9
 */
@Data
public class BankCardRequest {

    @NotNull(groups = UpdateValidate.class)
    private Long id;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 银行编码
     */
    private String bankCode;

}
