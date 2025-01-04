package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.detail.OuterSubAccountDetail;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDetailDO;
import com.anypluspay.account.infra.persistence.dataobject.OuterSubAccountDetailDO;
import com.anypluspay.commons.convertor.WriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2023/12/23
 */

@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface OuterAccountDetailDalConvertor extends WriteConvertor<OuterAccountDetail, OuterAccountDetailDO> {

    @Mapping(target = "beforeBalance", expression = "java(toMoney(outerAccountDetailDO.getBeforeBalance(), outerAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "afterBalance", expression = "java(toMoney(outerAccountDetailDO.getAfterBalance(), outerAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "amount", expression = "java(toMoney(outerAccountDetailDO.getAmount(), outerAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "outerSubAccountDetails", expression = "java(subToEntity(outerSubAccountDetailDOs))")
    OuterAccountDetail toEntity(OuterAccountDetailDO outerAccountDetailDO, List<OuterSubAccountDetailDO> outerSubAccountDetailDOs);

    @Mapping(target = "currencyCode", expression = "java(outerAccountDetail.getBeforeBalance().getCurrency().getCurrencyCode())")
    @Mapping(target = "beforeBalance", expression = "java(outerAccountDetail.getBeforeBalance().getAmount())")
    @Mapping(target = "afterBalance", expression = "java(outerAccountDetail.getAfterBalance().getAmount())")
    @Mapping(target = "amount", expression = "java(outerAccountDetail.getAmount().getAmount())")
    @Override
    OuterAccountDetailDO toDO(OuterAccountDetail outerAccountDetail);

    @Mapping(target = "currencyCode", expression = "java(outerSubAccountDetail.getBeforeBalance().getCurrency().getCurrencyCode())")
    @Mapping(target = "beforeBalance", expression = "java(outerSubAccountDetail.getBeforeBalance().getAmount())")
    @Mapping(target = "afterBalance", expression = "java(outerSubAccountDetail.getAfterBalance().getAmount())")
    @Mapping(target = "amount", expression = "java(outerSubAccountDetail.getAmount().getAmount())")
    OuterSubAccountDetailDO subToDO(OuterSubAccountDetail outerSubAccountDetail);

    @Mapping(target = "beforeBalance", expression = "java(toMoney(outerSubAccountDetailDO.getBeforeBalance(), outerSubAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "afterBalance", expression = "java(toMoney(outerSubAccountDetailDO.getAfterBalance(), outerSubAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "amount", expression = "java(toMoney(outerSubAccountDetailDO.getAmount(), outerSubAccountDetailDO.getCurrencyCode()))")
    OuterSubAccountDetail subToEntity(OuterSubAccountDetailDO outerSubAccountDetailDO);

    default List<OuterSubAccountDetail> subToEntity(List<OuterSubAccountDetailDO> outerSubAccountDetailDOS) {
        List<OuterSubAccountDetail> outerSubAccountDetails = new ArrayList<>();
        if (outerSubAccountDetailDOS != null) {
            outerSubAccountDetailDOS.forEach(entityType -> outerSubAccountDetails.add(subToEntity(entityType)));
        }
        return outerSubAccountDetails;
    }
}
