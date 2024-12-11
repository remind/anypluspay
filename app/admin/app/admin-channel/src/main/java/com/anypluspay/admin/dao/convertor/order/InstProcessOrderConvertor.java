package com.anypluspay.admin.dao.convertor.order;

import com.anypluspay.admin.dao.convertor.ConvertorUtils;
import com.anypluspay.admin.dao.convertor.SimpleQueryConvertor;
import com.anypluspay.admin.model.order.InstOrderDto;
import com.anypluspay.admin.model.order.InstProcessOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.InstProcessOrderDO;
import com.anypluspay.component.dal.PageConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface InstProcessOrderConvertor extends SimpleQueryConvertor<InstProcessOrderDto, InstProcessOrderDO> {
    @Mapping(target = "statusName", expression = "java(ConvertorUtils.toInstProcessOrderStatusName(doObject.getStatus()))")
    InstProcessOrderDto toDto(InstProcessOrderDO doObject);
}