package com.anypluspay.payment.facade.member;

import lombok.Data;

/**
 * @author wxj
 * 2025/7/8
 */
@Data
public class MemberBankCardRequest {

    private Long id;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 身份证号码
     */
    private String cardIdNo;

    /**
     * 姓名
     */
    private String cardName;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 手机号
     */
    private String mobile;
}
