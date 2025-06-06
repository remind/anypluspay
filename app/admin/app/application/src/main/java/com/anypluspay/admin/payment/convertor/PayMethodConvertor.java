package com.anypluspay.admin.payment.convertor;

import com.anypluspay.admin.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.payment.request.PayMethodRequest;
import com.anypluspay.admin.payment.response.PayMethodResponse;
import com.anypluspay.payment.infra.persistence.dataobject.PayMethodDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/5/28
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface PayMethodConvertor extends SimpleCrudConvertor<PayMethodResponse, PayMethodRequest, PayMethodDO> {

    @Mapping(target = "payModelName", expression = "java(ConvertorUtils.toPayModelName(doObject.getPayModel()))")
    @Mapping(target = "assetTypeName", expression = "java(ConvertorUtils.toAssetTypeName(doObject.getAssetType()))")
    @Mapping(target = "statusName", expression = "java(ConvertorUtils.toPayMethodStatusName(doObject.getStatus()))")
    @Override
    PayMethodResponse toDto(PayMethodDO doObject);
}
