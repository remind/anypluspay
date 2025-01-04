package com.anypluspay.channel.domain.convertor;

import com.anypluspay.channel.types.channel.ChannelApiProtocol;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.InstAbility;
import com.anypluspay.channel.types.enums.*;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.SubmitTimeType;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2024/8/2
 */
@Mapper(componentModel = "spring")
public interface EnumsConvertor  {

    default BizOrderStatus toBizOrderStatus(String code) {
        return EnumUtil.getByCode(BizOrderStatus.class, code);
    }

    default InstOrderStatus toInstOrderStatus(String code) {
        return EnumUtil.getByCode(InstOrderStatus.class, code);
    }

    default ChannelApiType toChannelApiType(String code) {
        return EnumUtil.getByCode(ChannelApiType.class, code);
    }

    default SubmitTimeType toSubmitTimeType(String code) {
        return EnumUtil.getByCode(SubmitTimeType.class, code);
    }

    default RefundType toRefundType(String code) {
        return EnumUtil.getByCode(RefundType.class, code);
    }

    default ChannelApiProtocol toChannelApiProtocol(String code) {
        return EnumUtil.getByCode(ChannelApiProtocol.class, code);
    }

    default CardType toCardType(String code) {
        return EnumUtil.getByCode(CardType.class, code);
    }

    default CompanyOrPersonal toCompanyOrPersonal(String code) {
        return EnumUtil.getByCode(CompanyOrPersonal.class, code);
    }

    default InstAbility toInstType(String code) {
        return EnumUtil.getByCode(InstAbility.class, code);
    }

    default RequestType toRequestType(String code) {
        return EnumUtil.getByCode(RequestType.class, code);
    }

    default TaskStatus toTaskStatus(String code) {
        return EnumUtil.getByCode(TaskStatus.class, code);
    }

}
