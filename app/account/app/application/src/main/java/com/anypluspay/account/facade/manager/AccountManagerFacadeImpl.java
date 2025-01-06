package com.anypluspay.account.facade.manager;

import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.repository.InnerAccountRepository;
import com.anypluspay.account.domain.repository.OuterAccountRepository;
import com.anypluspay.account.domain.service.OuterAccountDomainService;
import com.anypluspay.account.facade.manager.builder.InnerAccountBuilder;
import com.anypluspay.account.facade.manager.builder.OuterAccountBuilder;
import com.anypluspay.account.facade.manager.convertor.InnerAccountConvertor;
import com.anypluspay.account.facade.manager.convertor.OuterAccountConvertor;
import com.anypluspay.account.facade.manager.dto.InnerAccountRequest;
import com.anypluspay.account.facade.manager.dto.OuterAccountAddRequest;
import com.anypluspay.account.facade.manager.response.InnerAccountResponse;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.account.types.enums.DenyStatus;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户管理
 *
 * @author wxj
 * 2023/12/22
 */
@RestController
@Slf4j
public class AccountManagerFacadeImpl implements AccountManagerFacade {

    @Autowired
    private OuterAccountBuilder outerAccountBuilder;

    @Autowired
    private InnerAccountBuilder innerAccountBuilder;

    @Autowired
    private InnerAccountConvertor innerAccountConvertor;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private InnerAccountRepository innerAccountRepository;

    @Autowired
    private OuterAccountRepository outerAccountRepository;

    @Autowired
    private OuterAccountConvertor outerAccountConvertor;

    @Autowired
    private OuterAccountDomainService outerAccountDomainService;

    @Override
    public OuterAccountResponse createOuterAccount(OuterAccountAddRequest request) {
        try {
            OuterAccount account = outerAccountBuilder.build(request);
            String accountNo = transactionTemplate.execute(status -> outerAccountRepository.store(account));
            return queryOuterAccount(accountNo);
        } catch (Exception e) {
            log.error("外部户开户失败,memberId=" + request.getMemberId(), e);
            throw new BizException(e);
        }
    }

    @Override
    public List<OuterAccountResponse> createOuterAccount(List<OuterAccountAddRequest> requests) {
        List<OuterAccount> outerAccounts = outerAccountBuilder.build(requests);
        List<OuterAccountResponse> responses = new ArrayList<>();
        transactionTemplate.executeWithoutResult(status -> {
            for (OuterAccount outerAccount : outerAccounts) {
                String accountNo = outerAccountRepository.store(outerAccount);
                responses.add(queryOuterAccount(accountNo));
            }
        });
        return responses;
    }

    @Override
    public String createInnerAccount(InnerAccountRequest request) {
        try {
            InnerAccount account = innerAccountBuilder.build(request);
            return transactionTemplate.execute(status -> innerAccountRepository.store(account));
        } catch (Exception e) {
            log.error("内部户开户失败,titleCode=" + request.getTitleCode(), e);
            throw new BizException(e);
        }
    }

    @Override
    public void updateInnerAccount(InnerAccountRequest request) {
        InnerAccount account = innerAccountRepository.load(request.getAccountNo());
        AssertUtil.notNull(account, "账户不存在");
        account.setAccountName(request.getAccountName());
        account.setMemo(request.getMemo());
        innerAccountRepository.reStore(account);
    }

    @Override
    public void deleteInnerAccount(String accountNo) {
        InnerAccount account = innerAccountRepository.load(accountNo);
        AssertUtil.notNull(account, "账户不存在");
        innerAccountRepository.delete(accountNo);
    }

    @Override
    public void changeDenyStatus(String accountNo, String denyStatusCode) {
        transactionTemplate.executeWithoutResult(status -> {
            OuterAccount outerAccount = outerAccountRepository.lock(accountNo);
            DenyStatus denyStatus = EnumUtil.getByCode(DenyStatus.class, denyStatusCode);
            AssertUtil.notNull(denyStatus, "状态不存在");
            outerAccountDomainService.changeDenyStatus(outerAccount, denyStatus);
        });
    }

    @Override
    public OuterAccountResponse queryOuterAccount(String accountNo) {
        OuterAccount outerAccount = outerAccountRepository.load(accountNo);
        return outerAccountConvertor.toResponse(outerAccount);
    }

    @Override
    public InnerAccountResponse queryInnerAccount(String accountNo) {
        InnerAccount account = innerAccountRepository.load(accountNo);
        return innerAccountConvertor.toResponse(account);
    }
}
