package com.anypluspay.admin.member.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.types.member.MemberStatus;
import com.anypluspay.payment.types.member.MemberType;

/**
 * @author wxj
 * 2025/5/27
 */
public class ConvertorUtils extends GlobalConvertorUtils {

    public static String toMemberStatusName(String code) {
        return StrUtil.isNotBlank(code) ? EnumUtil.getByCode(MemberStatus.class, code).getDisplayName() : "";
    }

    public static String toMemberTypeName(String code) {
        return StrUtil.isNotBlank(code) ? EnumUtil.getByCode(MemberType.class, code).getDisplayName() : "";
    }

}
