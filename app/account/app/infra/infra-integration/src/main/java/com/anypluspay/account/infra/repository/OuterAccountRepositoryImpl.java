package com.anypluspay.account.infra.repository;

import com.anypluspay.account.domain.AccountDomainConstants;
import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.repository.OuterAccountRepository;
import com.anypluspay.account.infra.persistence.convertor.OuterAccountDalConvertor;
import com.anypluspay.account.infra.persistence.convertor.OuterSubAccountDalConvertor;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDO;
import com.anypluspay.account.infra.persistence.dataobject.OuterSubAccountDO;
import com.anypluspay.account.infra.persistence.mapper.OuterAccountMapper;
import com.anypluspay.account.infra.persistence.mapper.OuterSubAccountMapper;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.GlobalResultCode;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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
    private OuterSubAccountMapper outerSubAccountMapper;

    @Autowired
    private OuterAccountDalConvertor dalConvertor;

    @Autowired
    private OuterSubAccountDalConvertor outerSubAccountDalConvertor;

    @Override
    public String store(OuterAccount account) {
        AssertUtil.isFalse(isExists(account.getMemberId(), account.getAccountType()), "该用户的账户类型已经存在");
        String accountNo = genAccountNo(account.getMemberId(), account.getTitleCode(), account.getCurrencyCode());
        account.setAccountNo(accountNo);
        OuterAccountDO outerAccountDO = dalConvertor.toOuterAccountDo(account);
        List<OuterSubAccountDO> outerSubAccountDOS = outerSubAccountDalConvertor.toDO(account.getOuterSubAccounts());
        outerAccountMapper.insert(outerAccountDO);
        outerSubAccountDOS.forEach(outerSubAccountDO -> outerSubAccountMapper.insert(outerSubAccountDO));
        return accountNo;
    }

    @Override
    public void reStore(OuterAccount account) {
        AssertUtil.notNull(account, GlobalResultCode.ILLEGAL_PARAM);
        OuterAccountDO outerAccountDO = dalConvertor.toOuterAccountDo(account);
        List<OuterSubAccountDO> outerSubAccountDOS = outerSubAccountDalConvertor.toDO(account.getOuterSubAccounts());
        outerAccountMapper.update(outerAccountDO, getOuterAccountIdWrapper(account.getAccountNo()));
        outerSubAccountDOS.forEach(outerSubAccountDO -> {
            outerSubAccountMapper.update(outerSubAccountDO
                    , getOuterSubAccountIdWrapper(outerSubAccountDO.getAccountNo(), outerSubAccountDO.getFundType()));
        });
    }

    @Override
    public OuterAccount load(String accountNo) {
        AssertUtil.notNull(accountNo, GlobalResultCode.ILLEGAL_PARAM);
        OuterAccount outerAccount = null;
        OuterAccountDO outerAccountDO = outerAccountMapper.selectOne(getOuterAccountIdWrapper(accountNo));
        if (outerAccountDO != null) {
            List<OuterSubAccountDO> outerSubAccountDOS = outerSubAccountMapper.selectList(Wrappers.lambdaQuery(OuterSubAccountDO.class)
                    .eq(OuterSubAccountDO::getAccountNo, outerAccountDO.getAccountNo()));
            outerAccount = dalConvertor.toEntity(outerAccountDO, outerSubAccountDOS);
        }
        return outerAccount;
    }

    @Override
    public OuterAccount lock(String accountNo) {
        AssertUtil.notNull(accountNo, GlobalResultCode.ILLEGAL_PARAM);
        OuterAccount outerAccount = null;
        OuterAccountDO outerAccountDO = outerAccountMapper.lockOne(getOuterAccountIdWrapper(accountNo));
        if (outerAccountDO != null) {
            List<OuterSubAccountDO> outerSubAccountDOS = outerSubAccountMapper.selectList(Wrappers.lambdaQuery(OuterSubAccountDO.class)
                    .eq(OuterSubAccountDO::getAccountNo, outerAccountDO.getAccountNo()));
            outerAccount = dalConvertor.toEntity(outerAccountDO, outerSubAccountDOS);
        }
        return outerAccount;
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

    private Wrapper<OuterAccountDO> getOuterAccountIdWrapper(String accountNo) {
        return new LambdaQueryWrapper<OuterAccountDO>().eq(OuterAccountDO::getAccountNo, accountNo);
    }

    private Wrapper<OuterSubAccountDO> getOuterSubAccountIdWrapper(String accountNo, String fundType) {
        return new LambdaQueryWrapper<OuterSubAccountDO>().eq(OuterSubAccountDO::getAccountNo, accountNo)
                .eq(OuterSubAccountDO::getFundType, fundType);
    }
}
