package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.member.Member;

/**
 * @author wxj
 * 2025/5/27
 */
public interface MemberRepository {

    void store(Member member);

    void reStore(Member member);

    Member load(String memberId);

    Member lock(String memberId);

    void delete(String memberId);

}
