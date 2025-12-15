package com.anypluspay.anypay.infra.persistence.member;

import com.anypluspay.anypay.infra.persistence.mapper.MemberMapper;
import com.anypluspay.anypay.infra.persistence.member.convertor.MemberDalConvertor;
import com.anypluspay.anypay.member.Member;
import com.anypluspay.anypay.member.repository.MemberRepository;
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
