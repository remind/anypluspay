package com.anypluspay.admin.dao.convertor.order;

import com.anypluspay.admin.dao.convertor.ConvertorUtils;
import com.anypluspay.basis.convertor.SimpleQueryConvertor;
import com.anypluspay.admin.model.order.BizOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface BizOrderConvertor extends SimpleQueryConvertor<BizOrderDto, BizOrderDO> {
    @Mapping(target = "statusName", expression = "java(ConvertorUtils.toBizOrderStatusName(doObject.getStatus()))")
    BizOrderDto toDto(BizOrderDO doObject);
}
