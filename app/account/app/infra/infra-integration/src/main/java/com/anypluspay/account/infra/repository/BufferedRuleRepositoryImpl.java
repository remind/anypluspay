package com.anypluspay.account.infra.repository;

import com.anypluspay.account.domain.buffer.BufferedRule;
import com.anypluspay.account.domain.repository.BufferedRuleRepository;
import com.anypluspay.account.infra.persistence.convertor.BufferedRuleDalConvertor;
import com.anypluspay.account.infra.persistence.dataobject.BufferedRuleDO;
import com.anypluspay.account.infra.persistence.mapper.BufferedRuleMapper;
import com.anypluspay.account.types.enums.CrDr;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2023/12/25
 */
@Repository
public class BufferedRuleRepositoryImpl implements BufferedRuleRepository {

    @Autowired
    private BufferedRuleMapper mapper;

    @Autowired
    private BufferedRuleDalConvertor dalConvertor;

    @Override
    public void store(BufferedRule rule) {

    }

    @Override
    public void reStore(BufferedRule rule) {

    }

    @Override
    public boolean isExists(String accountNo, CrDr crDr) {
        Wrapper<BufferedRuleDO> wrapper = new LambdaQueryWrapper<BufferedRuleDO>()
                .eq(BufferedRuleDO::getAccountNo, accountNo)
                .and(a -> a.eq(BufferedRuleDO::getCrDr, crDr.getCode())
                        .or(b -> b.isNull(BufferedRuleDO::getCrDr)));
        return mapper.selectCount(wrapper) > 0;
    }
}
