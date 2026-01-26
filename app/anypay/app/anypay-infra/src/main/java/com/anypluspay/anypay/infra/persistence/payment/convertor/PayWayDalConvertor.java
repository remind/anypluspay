package com.anypluspay.anypay.infra.persistence.payment.convertor;

import com.anypluspay.anypay.domain.pay.PayWay;
import com.anypluspay.anypay.infra.persistence.dataobject.PayWayDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2026/1/26
 */
@Mapper(componentModel = "spring")
public interface PayWayDalConvertor extends ReadWriteConvertor<PayWay, PayWayDO> {
}
