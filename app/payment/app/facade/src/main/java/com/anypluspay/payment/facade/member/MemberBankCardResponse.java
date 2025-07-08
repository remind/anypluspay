package com.anypluspay.payment.facade.member;

import lombok.Builder;
import lombok.Data;

/**
 * @author wxj
 * 2025/7/8
 */
@Data
@Builder
public class MemberBankCardResponse {

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
     * 银行名称
     */
    private String bankName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 最后修改时间
     */
    private String gmtModified;
}
