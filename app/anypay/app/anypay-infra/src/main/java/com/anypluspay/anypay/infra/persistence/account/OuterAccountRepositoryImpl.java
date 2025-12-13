package com.anypluspay.anypay.infra.persistence.account;


import com.anypluspay.anypay.account.AccountDomainConstants;
import com.anypluspay.anypay.account.OuterAccount;
import com.anypluspay.anypay.account.repository.OuterAccountRepository;
import com.anypluspay.anypay.infra.persistence.account.convertor.OuterAccountDalConvertor;
import com.anypluspay.anypay.infra.persistence.dataobject.OuterAccountDO;
import com.anypluspay.anypay.infra.persistence.mapper.OuterAccountMapper;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.GlobalResultCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * 外部账户仓储实现
 *
 * @author wxj
 * 2023/12/16
 */
@Repository
public class OuterAccountRepositoryImpl implements OuterAccountRepository {

    @Autowired
    private OuterAccountMapper outerAccountMapper;

    @Autowired
    private OuterAccountDalConvertor dalConvertor;

    @Override
    public String store(OuterAccount account) {
        AssertUtil.isFalse(isExists(account.getMemberId(), account.getAccountType()), "该用户的账户类型已经存在");
        String accountNo = genAccountNo(account.getMemberId(), account.getTitleCode(), account.getCurrencyCode());
        account.setAccountNo(accountNo);
        OuterAccountDO outerAccountDO = dalConvertor.toOuterAccountDo(account);
        outerAccountMapper.insert(outerAccountDO);
        return accountNo;
    }

    @Override
    public void reStore(OuterAccount account) {
        AssertUtil.notNull(account, GlobalResultCode.ILLEGAL_PARAM);
        OuterAccountDO outerAccountDO = dalConvertor.toOuterAccountDo(account);
        outerAccountMapper.updateById(outerAccountDO);
    }

    @Override
    public OuterAccount load(String accountNo) {
        AssertUtil.notNull(accountNo, GlobalResultCode.ILLEGAL_PARAM);
        OuterAccountDO outerAccountDO = outerAccountMapper.selectById(accountNo);
        return dalConvertor.toEntity(outerAccountDO);
    }

    @Override
    public OuterAccount lock(String accountNo) {
        AssertUtil.notNull(accountNo, GlobalResultCode.ILLEGAL_PARAM);
        OuterAccountDO outerAccountDO = outerAccountMapper.lockById(accountNo);
        return dalConvertor.toEntity(outerAccountDO);
    }

    @Override
    public OuterAccount queryByMemberIdAndAccountType(String memberId, String accountType) {
        LambdaQueryWrapper<OuterAccountDO> wrapper = Wrappers.lambdaQuery(OuterAccountDO.class)
                .eq(OuterAccountDO::getMemberId, memberId)
                .eq(OuterAccountDO::getAccountType, accountType);
        OuterAccountDO outerAccountDO = outerAccountMapper.selectOne(wrapper);
        return dalConvertor.toEntity(outerAccountDO);
    }

    @Override
    public List<OuterAccount> queryByMemberId(String memberId) {
        LambdaQueryWrapper<OuterAccountDO> wrapper = Wrappers.lambdaQuery(OuterAccountDO.class)
                .eq(OuterAccountDO::getMemberId, memberId);
        List<OuterAccountDO> outerAccountDOs = outerAccountMapper.selectList(wrapper);
        List<OuterAccount> outerAccounts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(outerAccountDOs)) {
            outerAccountDOs.forEach(outerAccountDO -> {
                outerAccounts.add(dalConvertor.toEntity(outerAccountDO));
            });
        }
        return outerAccounts;
    }

    private String genAccountNo(String memberId, String accountTitleNo, String currencyCode) {
        String incId = getIncId(memberId, accountTitleNo);
        return accountTitleNo + memberId + Currency.getInstance(currencyCode).getNumericCode() + incId;
    }

    private String getIncId(String memberId, String accountTitleNo) {
        String accountNoPrefix = accountTitleNo + memberId;
        int maxLength = String.valueOf(AccountDomainConstants.OUTER_ACCOUNT_NO_MAX_INC).length();
        List<OuterAccountDO> outerAccountDOS = outerAccountMapper.selectList(new LambdaQueryWrapper<OuterAccountDO>()
                .eq(OuterAccountDO::getMemberId, memberId)
                .like(OuterAccountDO::getAccountNo, accountNoPrefix)
                .orderByDesc(OuterAccountDO::getAccountNo));
        int intMaxNo = 0;
        if (!CollectionUtils.isEmpty(outerAccountDOS)) {
            String maxNo = outerAccountDOS.get(0).getAccountNo().substring(outerAccountDOS.get(0).getAccountNo().length() - maxLength);
            intMaxNo = Integer.parseInt(maxNo);
            AssertUtil.isTrue(intMaxNo > 0 && intMaxNo < AccountDomainConstants.OUTER_ACCOUNT_NO_MAX_INC, "用户账户已经超过最大个数");
        }
        return String.format("%0" + maxLength + "d", intMaxNo + 1);
    }

    private boolean isExists(String memberId, String accountType) {
        return outerAccountMapper.exists(new LambdaQueryWrapper<OuterAccountDO>().eq(OuterAccountDO::getMemberId, memberId)
                .eq(OuterAccountDO::getAccountType, accountType));
    }

}
