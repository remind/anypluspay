package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/6
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface InstOrderDalConvertor extends BaseExpressionConvertor {

    InstOrderDO toDO(InstOrder instFundOrder, String orderType);

    InstOrder toEntity(InstOrderDO instFundOrderDO);
}
