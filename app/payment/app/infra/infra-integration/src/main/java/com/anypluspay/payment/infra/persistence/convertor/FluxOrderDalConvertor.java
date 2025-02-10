package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.infra.persistence.dataobject.FluxOrderDO;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2024/1/29
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface FluxOrderDalConvertor extends ReadWriteConvertor<FluxOrder, FluxOrderDO> {


}
