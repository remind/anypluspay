package com.anypluspay.account.application.convertor;

import com.anypluspay.account.domain.AccountTransaction;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.BufferedDetail;
import com.anypluspay.account.domain.detail.InnerAccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.dto.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.types.enums.AccountFamily;
import org.mapstruct.Mapper;

import java.util.Objects;

/**
 * @author wxj
 * 2023/12/20
 */
@Mapper(componentModel = "spring")
public interface AccountingRequestConvertor {

    AccountTransaction toAccountTransaction(AccountingRequest request);

    OuterAccountDetail toOuterAccountDetail(AccountingRequest request, EntryDetail detail);

    InnerAccountDetail toInnerAccountDetail(AccountingRequest request, EntryDetail detail);

    BufferedDetail toBufferedDetail(AccountingRequest request, EntryDetail detail);

    OuterAccountDetail toOuterAccountDetail(BufferedDetail bufferedDetail);

    InnerAccountDetail toInnerAccountDetail(BufferedDetail bufferedDetail);

    default AccountDetail toAccountDetail(AccountingRequest request, EntryDetail entryDetail) {
        AccountFamily accountFamily = AccountUtil.getAccountFamily(entryDetail.getAccountNo());
        return switch (Objects.requireNonNull(accountFamily)) {
            case INNER -> toInnerAccountDetail(request, entryDetail);
            case OUTER -> toOuterAccountDetail(request, entryDetail);
        };
    }
    default AccountDetail toAccountDetail(BufferedDetail bufferedDetail) {
        AccountFamily accountFamily = AccountUtil.getAccountFamily(bufferedDetail.getAccountNo());
        return switch (Objects.requireNonNull(accountFamily)) {
            case INNER -> toInnerAccountDetail(bufferedDetail);
            case OUTER -> toOuterAccountDetail(bufferedDetail);
        };
    }

}
