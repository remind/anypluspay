package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.infra.persistence.dataobject.InstCommandOrderDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/6
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface InstCommandOrderDalConvertor extends ReadWriteConvertor<InstCommandOrder, InstCommandOrderDO> {

}
