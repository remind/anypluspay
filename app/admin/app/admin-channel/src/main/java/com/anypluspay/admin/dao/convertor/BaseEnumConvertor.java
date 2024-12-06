package com.anypluspay.admin.dao.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.MaintainTimeType;
import com.anypluspay.channel.types.enums.CardType;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.channel.types.order.InstProcessOrderStatus;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Objects;

/**
 * 基础枚举转换
 * @author wxj
 * 2024/12/3
 */
@Mapper(componentModel = "spring")
public interface BaseEnumConvertor {

    @Named("toApiTypeName")
    default String toApiTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(ChannelApiType.class, code)).getDisplayName() : "";
    }

    @Named("toInstProcessOrderStatusName")
    default String toInstProcessOrderStatusName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(InstProcessOrderStatus.class, code)).getDisplayName() : "";
    }

    @Named("toMaintainTimeTypeName")
    default String toMaintainTimeTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(MaintainTimeType.class, code)).getDisplayName() : "";
    }

    @Named("toCardTypeName")
    default String toCardTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(CardType.class, code)).getDisplayName() : "";
    }

    @Named("toRequestTypeName")
    default String toRequestTypeName(String code) {
        return StrUtil.isNotBlank(code) ? Objects.requireNonNull(EnumUtil.getByCode(RequestType.class, code)).getDisplayName() : "";
    }
}
