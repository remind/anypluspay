package com.anypluspay.admin.dao.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.InstAbility;
import com.anypluspay.channel.types.channel.MaintainTimeType;
import com.anypluspay.channel.types.enums.CardType;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.lang.utils.EnumUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wxj
 * 2024/12/5
 */
public class ConvertorUtils extends GlobalConvertorUtils {

    public static String toApiTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(ChannelApiType.class, code)).getDisplayName() : "";
    }

    public static String toMaintainTimeTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(MaintainTimeType.class, code)).getDisplayName() : "";
    }

    public static String toCardTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(CardType.class, code)).getDisplayName() : "";
    }

    public static String toRequestTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(RequestType.class, code)).getDisplayName() : "";
    }

    public static String toInstAbilityName(String instAbility) {
        if (StrUtil.isNotBlank(instAbility)) {
            List<String> codes = toList(instAbility);
            List<String> names = new ArrayList<>();
            codes.forEach(code -> names.add(Objects.requireNonNull(EnumUtil.getByCode(InstAbility.class, code)).getDisplayName()));
            return toStr(names);
        }
        return "";
    }

    public static String toRefundTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(RefundType.class, code)).getDisplayName() : "";
    }

    public static String toBizOrderStatusName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(BizOrderStatus.class, code)).getDisplayName() : "";
    }

    public static String toInstOrderStatusName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(InstOrderStatus.class, code)).getDisplayName() : "";
    }
}
