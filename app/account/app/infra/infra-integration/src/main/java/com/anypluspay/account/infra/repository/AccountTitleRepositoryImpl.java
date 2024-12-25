package com.anypluspay.account.infra.repository;

import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.domain.repository.AccountTitleRepository;
import com.anypluspay.account.infra.persistence.convertor.AccountTitleDalConvertor;
import com.anypluspay.account.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.account.infra.persistence.mapper.AccountTitleMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2023/12/16
 */
@Repository
public class AccountTitleRepositoryImpl implements AccountTitleRepository {

    @Autowired
    private AccountTitleMapper accountTitleMapper;

    @Autowired
    private AccountTitleDalConvertor dalConvertor;

    @Override
    public void store(AccountTitle accountTitle) {
        accountTitleMapper.insert(dalConvertor.toDO(accountTitle));
    }

    @Override
    public void reStore(AccountTitle accountTitle) {
        accountTitleMapper.updateById(dalConvertor.toDO(accountTitle));
    }

    @Override
    public AccountTitle load(String titleCode) {
        return dalConvertor.toEntity(accountTitleMapper.selectOne(getIdWrapper(titleCode)));
    }

    @Override
    public AccountTitle lock(String titleCode) {
        return null;
    }

    private Wrapper<AccountTitleDO> getIdWrapper(String code) {
        return new LambdaQueryWrapper<AccountTitleDO>().eq(AccountTitleDO::getCode, code);
    }
}
