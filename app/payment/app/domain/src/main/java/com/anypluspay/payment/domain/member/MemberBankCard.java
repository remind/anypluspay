package com.anypluspay.payment.domain.member;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/7/8
 */
@Data
@Builder
public class MemberBankCard {

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

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}
