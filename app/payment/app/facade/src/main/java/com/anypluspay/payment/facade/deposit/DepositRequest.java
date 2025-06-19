package com.anypluspay.payment.facade.deposit;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author wxj
 * 2025/5/14
 */
@Data
public class DepositRequest {

    /**
     * 合作方
     */
    @NotBlank(message = "合作方不能为空")
    @Length(min = 6, max = 15, message = "合作方长度为6-15")
    private String partnerId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 备注
     */
    private String memo;

    /**
     * 付款资金明细
     */
    private List<FundDetailInfo> payerFundDetail;
}
