package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 提现请求
 * @author wxj
 * 2025/5/15
 */
@Data
public class WithdrawRequest {

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
     * 出款账户
     */
    private String accountNo;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 身份证号码
     */
    private String cardIdNo;

    /**
     * 银行卡姓名
     */
    private String cardName;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 扩展信息
     */
    private String extension;

    /**
     * 备注
     */
    private String memo;
}
