package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.member.Member;
import com.anypluspay.payment.domain.repository.MemberRepository;
import com.anypluspay.payment.infra.persistence.convertor.MemberDalConvertor;
import com.anypluspay.payment.infra.persistence.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2025/5/27
 */
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberMapper dalMapper;

    @Autowired
    private MemberDalConvertor dalConvertor;

    @Override
    public void store(Member member) {
        dalMapper.insert(dalConvertor.toDO(member));
    }

    @Override
    public void reStore(Member member) {
        dalMapper.updateById(dalConvertor.toDO(member));
    }

    @Override
    public Member load(String memberId) {
        return dalConvertor.toEntity(dalMapper.selectById(memberId));
    }

    @Override
    public Member lock(String memberId) {
        return dalConvertor.toEntity(dalMapper.lockById(memberId));
    }

    @Override
    public void delete(String memberId) {
        dalMapper.deleteById(memberId);
    }
}
