package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.domain.institution.InstDelayOrder;
import com.anypluspay.channel.infra.persistence.dataobject.InstDelayOrderDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/19
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface InstDelayOrderDalConvertor extends BaseExpressionConvertor {

    InstDelayOrderDO toDO(InstDelayOrder instDelayOrder);

    InstDelayOrder toEntity(InstDelayOrderDO instDelayOrderDO);
}
