package com.anypluspay.admin.auth.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.account.types.accounting.AccountTitleScope;
import com.anypluspay.account.types.accounting.AccountTitleType;
import com.anypluspay.account.types.enums.*;
import com.anypluspay.admin.auth.SysUserStatus;
import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.lang.utils.EnumUtil;

import java.util.Objects;

/**
 *
 * @author wxj
 * 2024/12/26
 */
public class ConvertorUtils extends GlobalConvertorUtils {

    public static String toSysUserStatusName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(SysUserStatus.class, code)).getDisplayName() : "";
    }
}
