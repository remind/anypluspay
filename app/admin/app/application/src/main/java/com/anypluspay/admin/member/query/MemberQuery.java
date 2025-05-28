package com.anypluspay.admin.member.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * 会员查询
 * @author wxj
 * 2025/5/27
 */
@Data
public class MemberQuery extends PageQuery {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String name;

}
