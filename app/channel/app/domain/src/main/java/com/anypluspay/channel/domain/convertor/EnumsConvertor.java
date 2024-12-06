package com.anypluspay.channel.domain.convertor;

import com.anypluspay.channel.types.channel.ChannelApiProtocol;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.InstAbility;
import com.anypluspay.channel.types.enums.*;
import com.anypluspay.channel.types.order.*;
import com.anypluspay.commons.convertor.GlobalTypeConvertor;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2024/8/2
 */
@Mapper(componentModel = "spring")
public interface EnumsConvertor extends GlobalTypeConvertor {

    default BizOrderStatus toBizOrderStatus(String code) {
        return EnumUtil.getByCode(BizOrderStatus.class, code);
    }

    default InstOrderStatus toInstOrderStatus(String code) {
        return EnumUtil.getByCode(InstOrderStatus.class, code);
    }

    default ChannelApiType toChannelApiType(String code) {
        return EnumUtil.getByCode(ChannelApiType.class, code);
    }
    default PayMode toPayMode(String code) {
        return EnumUtil.getByCode(PayMode.class, code);
    }

    default InstProcessOrderStatus toInstOrderResultStatus(String code) {
        return EnumUtil.getByCode(InstProcessOrderStatus.class, code);
    }
    default DelayOrderStatus toDelayOrderStatus(String code) {
        return EnumUtil.getByCode(DelayOrderStatus.class, code);
    }

    default ProcessTimeType toProcessTimeType(String code) {
        return EnumUtil.getByCode(ProcessTimeType.class, code);
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

}
