package com.anypluspay.anypay.infra.persistence.member.convertor;

import com.anypluspay.anypay.infra.persistence.dataobject.MemberDO;
import com.anypluspay.anypay.member.Member;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2025/5/27
 */
@Mapper(componentModel = "spring")
public interface MemberDalConvertor extends ReadWriteConvertor<Member, MemberDO> {
}
