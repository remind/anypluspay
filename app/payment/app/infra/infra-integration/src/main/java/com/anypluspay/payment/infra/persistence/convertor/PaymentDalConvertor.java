package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.PaymentDO;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2025/2/23
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface PaymentDalConvertor extends ReadWriteConvertor<Payment, PaymentDO> {


}
