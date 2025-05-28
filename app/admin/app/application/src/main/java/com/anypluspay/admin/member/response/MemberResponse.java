package com.anypluspay.admin.member.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/5/27
 */
@Data
public class MemberResponse {

    /**
     * 会员号
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 会员类型
     */
    private String memberType;

    /**
     * 会员类型名称
     */
    private String memberTypeName;

    /**
     * 会员状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
