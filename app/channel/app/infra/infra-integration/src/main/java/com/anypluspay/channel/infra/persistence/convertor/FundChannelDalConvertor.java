package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.channel.fund.FundChannel;
import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.FundChannelDO;
import com.anypluspay.component.dal.PageConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/25
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface FundChannelDalConvertor extends PageConvertor<FundChannel, FundChannelDO> {

}
