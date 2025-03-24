package com.anypluspay.admin.channel.web.controller.example.convertor;

import com.anypluspay.admin.channel.web.controller.example.request.ExampleRefundRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/8
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExampleConvertor extends BaseExpressionConvertor {

    RefundRequest toRefundRequest(ExampleRefundRequest request);

    default RefundType toRefundType(String refundType) {
        return EnumUtil.getByCode(RefundType.class, refundType);
    }
}
