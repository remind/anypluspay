package com.anypluspay.admin.account.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.account.types.accounting.AccountTitleScope;
import com.anypluspay.account.types.accounting.AccountTitleType;
import com.anypluspay.account.types.enums.*;
import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.lang.utils.EnumUtil;

import java.util.Objects;

/**
 * @author wxj
 * 2024/12/26
 */
public class ConvertorUtils extends GlobalConvertorUtils {

    public static String toAccountTitleTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(AccountTitleType.class, code)).getDisplayName() : "";
    }

    public static String toBalanceDirectionName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(BalanceDirection.class, code)).getDisplayName() : "";
    }

    public static String toScopeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(AccountTitleScope.class, code)).getDisplayName() : "";
    }

    public static String toDenyStatusName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(DenyStatus.class, code)).getDisplayName() : "";
    }

    public static String toAccountAttributeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(AccountAttribute.class, code)).getDisplayName() : "";
    }

    public static String toOperationTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(OperationType.class, code)).getDisplayName() : "";
    }

    public static String toCrDrName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(CrDr.class, code)).getDisplayName() : "";
    }

    public static String toIoDirectionName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(IODirection.class, code)).getDisplayName(): "";
    }
}
