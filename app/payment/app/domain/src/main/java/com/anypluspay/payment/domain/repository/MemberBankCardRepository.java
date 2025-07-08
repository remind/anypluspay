package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.member.MemberBankCard;

import java.util.List;

/**
 * @author wxj
 * 2025/7/8
 */
public interface MemberBankCardRepository {

    MemberBankCard load(Long id);

    void store(MemberBankCard memberBankCard);

    void reStore(MemberBankCard memberBankCard);

    void delete(Long id);

    List<MemberBankCard> queryByMemberId(String memberId);
}
