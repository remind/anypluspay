package com.anypluspay.channel.infra.persistence.bizorder;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/9
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface BizOrderConvertor extends BaseExpressionConvertor {

    @Mapping(target = "requestRootType", expression = "java(toRequestRootType(baseBizOrder.getRequestType()))")
    @Mapping(target = "requestType", expression = "java(toRequestType(baseBizOrder.getRequestType()))")
    BizOrderDO toDO(BaseBizOrder baseBizOrder);

    default String toRequestType(RequestType requestType) {
        return requestType.getCode();
    }

    default String toRequestRootType(RequestType requestType) {
        return requestType.getRequestRootType().getCode();
    }
}
