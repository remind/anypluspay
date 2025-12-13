package com.anypluspay.anypay.infra.persistence.account.convertor;

import com.anypluspay.anypay.account.OuterAccount;
import com.anypluspay.anypay.infra.persistence.dataobject.OuterAccountDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * @author wxj
 * 2023/12/18
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface OuterAccountDalConvertor {

    @Mapping(target = "outerSubAccounts")
    OuterAccount toEntity(OuterAccountDO outerAccountDO);

    OuterAccountDO toOuterAccountDo(OuterAccount outerAccount);

}
