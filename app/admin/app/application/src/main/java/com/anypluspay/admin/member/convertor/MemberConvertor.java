package com.anypluspay.admin.member.convertor;

import com.anypluspay.admin.auth.convertor.ConvertorUtils;
import com.anypluspay.admin.basis.convertor.SimpleQueryConvertor;
import com.anypluspay.admin.member.response.MemberResponse;
import com.anypluspay.payment.infra.persistence.dataobject.MemberDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/5/27
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface MemberConvertor extends SimpleQueryConvertor<MemberResponse, MemberDO> {
}
