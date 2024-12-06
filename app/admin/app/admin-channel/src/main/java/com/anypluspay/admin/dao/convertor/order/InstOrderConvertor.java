package com.anypluspay.admin.dao.convertor.order;

import com.anypluspay.admin.model.order.InstOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.component.dal.PageConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstOrderConvertor extends PageConvertor<InstOrderDto, InstOrderDO> {

}
