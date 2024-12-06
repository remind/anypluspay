package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.domain.result.UnityResultCode;
import com.anypluspay.channel.infra.persistence.dataobject.UnityResultCodeDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/7
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface UnityResultCodeDalConvertor extends BaseExpressionConvertor {

    UnityResultCode convert(UnityResultCodeDO unityResultCodeDO);

    UnityResultCodeDO convert(UnityResultCode unityResultCode);
}
