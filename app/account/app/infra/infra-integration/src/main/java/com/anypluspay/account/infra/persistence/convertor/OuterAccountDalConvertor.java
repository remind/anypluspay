package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDO;
import com.anypluspay.account.infra.persistence.dataobject.OuterSubAccountDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author wxj
 * 2023/12/18
 */
@Mapper(componentModel = "spring", uses = {OuterSubAccountDalConvertor.class, EnumsConvertor.class})
public interface OuterAccountDalConvertor {

    @Mapping(target = "outerSubAccounts", source = "outerSubAccountDOs")
    OuterAccount toEntity(OuterAccountDO outerAccountDO, List<OuterSubAccountDO> outerSubAccountDOs);

    OuterAccountDO toOuterAccountDo(OuterAccount outerAccount);

}
