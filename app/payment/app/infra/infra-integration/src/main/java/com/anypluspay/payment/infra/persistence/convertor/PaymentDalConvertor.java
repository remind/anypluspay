package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.payment.domain.BasePayment;
import com.anypluspay.payment.domain.instant.InstantPayment;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.PaymentDO;
import com.anypluspay.payment.types.PaymentType;
import com.anypluspay.commons.convertor.WriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2024/1/18
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface PaymentDalConvertor extends WriteConvertor<BasePayment, PaymentDO> {

    default BasePayment toPayment(PaymentDO paymentDO) {
        BasePayment payment = null;
        if (paymentDO != null) {
            if (PaymentType.INSTANT.getCode().equals(paymentDO.getPaymentType())) {
                payment = toInstantPayment(paymentDO);
            }
        }
        return payment;
    }

    InstantPayment toInstantPayment(PaymentDO paymentDO);
}
