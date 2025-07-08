package com.anypluspay.payment.facade.member;

import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.enums.EnumObject;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.domain.member.MemberBankCard;
import com.anypluspay.payment.domain.repository.MemberBankCardRepository;
import com.anypluspay.payment.types.paymethod.BankCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wxj
 * 2025/7/8
 */
@RestController
public class MemberQueryFacadeImpl implements MemberQueryFacade {

    @Autowired
    private MemberBankCardRepository memberBankCardRepository;

    @Override
    public List<MemberBankCardResponse> queryBankCard(String memberId) {
        List<MemberBankCard> memberBankCards = memberBankCardRepository.queryByMemberId(memberId);
        return memberBankCards.stream().map(MemberQueryFacadeImpl::toMemberBankCardResponse).toList();
    }

    @Override
    public MemberBankCardResponse addBankCard(MemberBankCardRequest request) {
        MemberBankCard memberBankCard = toBankCard(request);
        memberBankCardRepository.store(memberBankCard);
        return toMemberBankCardResponse(memberBankCardRepository.load(memberBankCard.getId()));
    }

    @Override
    public MemberBankCardResponse updateBankCard(MemberBankCardRequest request) {
        MemberBankCard memberBankCard = toBankCard(request);
        memberBankCardRepository.reStore(memberBankCard);
        return toMemberBankCardResponse(memberBankCardRepository.load(memberBankCard.getId()));
    }

    @Override
    public void deleteBankCard(Long id) {
        memberBankCardRepository.delete(id);
    }

    @Override
    public List<EnumObject> queryBankCodes() {
        return EnumUtil.toEnumObjects(BankCode.class);
    }

    private MemberBankCard toBankCard(MemberBankCardRequest request) {
        return MemberBankCard.builder()
                .id(request.getId())
                .memberId(request.getMemberId())
                .cardNo(request.getCardNo())
                .cardIdNo(request.getCardIdNo())
                .cardName(request.getCardName())
                .bankCode(request.getBankCode())
                .mobile(request.getMobile())
                .build();
    }

    private static MemberBankCardResponse toMemberBankCardResponse(MemberBankCard memberBankCard) {
        return MemberBankCardResponse.builder()
                .id(memberBankCard.getId())
                .memberId(memberBankCard.getMemberId())
                .cardNo(memberBankCard.getCardNo())
                .cardIdNo(memberBankCard.getCardIdNo())
                .cardName(memberBankCard.getCardName())
                .bankCode(memberBankCard.getBankCode())
                .bankName(BankCode.getDisplayNameByCode(memberBankCard.getBankCode()))
                .mobile(memberBankCard.getMobile())
                .gmtCreate(GlobalConvertorUtils.dateToString(memberBankCard.getGmtCreate()))
                .gmtModified(GlobalConvertorUtils.dateToString(memberBankCard.getGmtModified()))
                .build();
    }
}
