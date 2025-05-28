package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.member.Member;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.MemberDO;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2025/5/27
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface MemberDalConvertor extends ReadWriteConvertor<Member, MemberDO> {
}
