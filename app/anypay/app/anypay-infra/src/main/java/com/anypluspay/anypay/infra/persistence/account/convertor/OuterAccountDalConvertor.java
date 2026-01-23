package com.anypluspay.anypay.infra.persistence.account.convertor;

import com.anypluspay.anypay.domain.account.OuterAccount;
import com.anypluspay.anypay.infra.persistence.dataobject.OuterAccountDO;
import org.mapstruct.Mapper;


/**
 * @author wxj
 * 2023/12/18
 */
@Mapper(componentModel = "spring", uses = {AccountEnumsConvertor.class})
public interface OuterAccountDalConvertor {

    OuterAccount toEntity(OuterAccountDO outerAccountDO);

    OuterAccountDO toOuterAccountDo(OuterAccount outerAccount);

}
