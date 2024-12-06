package com.anypluspay.admin.dao.convertor.order;

import com.anypluspay.admin.model.order.BizOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.component.dal.PageConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BizOrderConvertor extends PageConvertor<BizOrderDto, BizOrderDO> {

}
