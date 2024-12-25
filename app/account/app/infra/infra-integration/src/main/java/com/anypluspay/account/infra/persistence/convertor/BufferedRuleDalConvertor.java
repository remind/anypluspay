package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.buffer.BufferedRule;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.BufferedRuleDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/25
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface BufferedRuleDalConvertor extends ReadWriteConvertor<BufferedRule, BufferedRuleDO> {


}