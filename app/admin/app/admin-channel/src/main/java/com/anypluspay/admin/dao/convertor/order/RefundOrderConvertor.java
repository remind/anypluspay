package com.anypluspay.admin.dao.convertor.order;

import com.anypluspay.admin.dao.convertor.ConvertorUtils;
import com.anypluspay.admin.model.order.RefundOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/11
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface RefundOrderConvertor {

    default RefundOrderDto fill(RefundOrderDto refundOrderDto) {
        if (refundOrderDto != null) {
            refundOrderDto.setStatusName(ConvertorUtils.toBizOrderStatusName(refundOrderDto.getStatus()));
            refundOrderDto.setRefundTypeName(ConvertorUtils.toRefundTypeName(refundOrderDto.getRefundType()));
        }

        return refundOrderDto;
    }
}
