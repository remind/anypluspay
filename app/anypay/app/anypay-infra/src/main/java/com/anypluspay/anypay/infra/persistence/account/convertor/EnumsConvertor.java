package com.anypluspay.anypay.infra.persistence.account.convertor;


import com.anypluspay.anypay.types.account.*;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/16
 */
@Mapper(componentModel = "spring")
public interface EnumsConvertor {

    default AccountTitleType toAccountTitleType(String code) {
        return EnumUtil.getByCode(AccountTitleType.class, code);
    }

    default BalanceDirection toBalanceDirection(String code) {
        return EnumUtil.getByCode(BalanceDirection.class, code);
    }

    default AccountTitleScope toAccountTitleRange(String code) {
        return EnumUtil.getByCode(AccountTitleScope.class, code);
    }

    default AccountAttribute toAccountAttribute(String code) {
        return EnumUtil.getByCode(AccountAttribute.class, code);
    }

    default CrDr toCrDr(String code) {
        return EnumUtil.getByCode(CrDr.class, code);
    }

    default DenyStatus toDenyStatus(String code) {
        return EnumUtil.getByCode(DenyStatus.class, code);
    }

    default IODirection toIODirection(String code) {
        return EnumUtil.getByCode(IODirection.class, code);
    }


}
