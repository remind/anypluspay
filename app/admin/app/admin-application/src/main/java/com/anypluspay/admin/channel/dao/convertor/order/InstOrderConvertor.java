package com.anypluspay.admin.channel.dao.convertor.order;

import com.anypluspay.admin.channel.dao.convertor.ConvertorUtils;
import com.anypluspay.admin.basis.convertor.SimpleQueryConvertor;
import com.anypluspay.admin.channel.model.order.InstOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface InstOrderConvertor extends SimpleQueryConvertor<InstOrderDto, InstOrderDO> {
    @Mapping(target = "statusName", expression = "java(ConvertorUtils.toInstOrderStatusName(doObject.getStatus()))")
    InstOrderDto toDto(InstOrderDO doObject);
}