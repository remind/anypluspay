package com.anypluspay.channel.infra.persistence.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.domain.channel.ChannelSupportInst;
import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelSupportInstDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.commons.lang.types.NumberRang;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/28
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface ChannelSupportInstDalConvertor extends ReadWriteConvertor<ChannelSupportInst, ChannelSupportInstDO> {

    default NumberRang toRerAmountRange(String perMountRange) {
        if (StrUtil.isNotBlank(perMountRange)) {
            return NumberRang.parse(perMountRange);
        }
        return null;
    }

    default String toRerAmountRangeStr(NumberRang perMountRange) {
        return perMountRange == null ? null : perMountRange.toString();
    }
}
