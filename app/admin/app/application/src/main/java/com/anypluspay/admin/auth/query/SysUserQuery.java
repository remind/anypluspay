package com.anypluspay.admin.auth.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * 系统用户搜索
 *
 * @author wxj
 * 2025/5/26
 */
@Data
public class SysUserQuery extends PageQuery {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 登录名
     */
    private String username;


}
