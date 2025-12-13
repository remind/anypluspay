package com.anypluspay.anypay.infra.persistence.account;

import com.anypluspay.anypay.account.AccountDomainConstants;
import com.anypluspay.anypay.account.AccountTitle;
import com.anypluspay.anypay.account.repository.AccountTitleRepository;
import com.anypluspay.anypay.infra.persistence.account.convertor.AccountTitleDalConvertor;
import com.anypluspay.anypay.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.anypay.infra.persistence.mapper.AccountTitleMapper;
import com.anypluspay.anypay.types.account.AccountTitleType;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void delete(String titleCode) {
        accountTitleMapper.deleteById(titleCode);
    }

    @Override
    public String getTitleCode(AccountTitleType type, Integer tier, String parentCode) {
        LambdaQueryWrapper<AccountTitleDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AccountTitleDO::getType, type.getCode())
                .eq(AccountTitleDO::getTier, tier)
                .orderByDesc(AccountTitleDO::getCode)
        ;
        if (StringUtils.isBlank(parentCode)) {
            queryWrapper.isNull(AccountTitleDO::getParentCode);
        } else {
            queryWrapper.eq(AccountTitleDO::getParentCode, parentCode);
        }
        AccountTitleDO accountTitleDO = accountTitleMapper.selectOne(queryWrapper, false);
        int nextTitleNo = 1;
        if (accountTitleDO != null) {
            nextTitleNo = Integer.parseInt(StringUtils.right(accountTitleDO.getCode(), AccountDomainConstants.ACCOUNT_TITLE_PER_LEN)) + 1;
        }
        AssertUtil.isTrue(String.valueOf(nextTitleNo).length() <= AccountDomainConstants.ACCOUNT_TITLE_PER_LEN, "科目编号已超长");
        String prefix = tier == 1 ? type.getCode() : parentCode;
        return prefix + String.format("%0" + AccountDomainConstants.ACCOUNT_TITLE_PER_LEN + "d", nextTitleNo);
    }

    private Wrapper<AccountTitleDO> getIdWrapper(String code) {
        return new LambdaQueryWrapper<AccountTitleDO>().eq(AccountTitleDO::getCode, code);
    }

}
