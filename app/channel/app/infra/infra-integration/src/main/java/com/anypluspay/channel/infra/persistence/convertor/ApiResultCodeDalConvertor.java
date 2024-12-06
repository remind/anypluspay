package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.domain.result.ApiResultCode;
import com.anypluspay.channel.infra.persistence.dataobject.ApiResultCodeDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/7
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface ApiResultCodeDalConvertor extends BaseExpressionConvertor {

    ApiResultCodeDO toDO(ApiResultCode apiResultCode);

    ApiResultCode toEntity(ApiResultCodeDO apiResultCodeDO);
}
