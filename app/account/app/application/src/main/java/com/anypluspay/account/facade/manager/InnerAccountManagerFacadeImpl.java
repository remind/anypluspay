package com.anypluspay.account.facade.manager;

import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.domain.repository.InnerAccountRepository;
import com.anypluspay.account.facade.manager.builder.InnerAccountBuilder;
import com.anypluspay.account.facade.manager.convertor.InnerAccountConvertor;
import com.anypluspay.account.facade.manager.request.InnerAccountRequest;
import com.anypluspay.account.facade.manager.response.InnerAccountResponse;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内部户管理
 *
 * @author wxj
 * 2023/12/22
 */
@RestController
@Slf4j
public class InnerAccountManagerFacadeImpl implements InnerAccountManagerFacade {

    @Autowired
    private InnerAccountBuilder innerAccountBuilder;

    @Autowired
    private InnerAccountConvertor innerAccountConvertor;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private InnerAccountRepository innerAccountRepository;

    @Override
    public String create(InnerAccountRequest request) {
        try {
            InnerAccount account = innerAccountBuilder.build(request);
            return transactionTemplate.execute(status -> innerAccountRepository.store(account));
        } catch (Exception e) {
            log.error("内部户开户失败,titleCode=" + request.getTitleCode(), e);
            throw new BizException(e);
        }
    }

    @Override
    public void update(InnerAccountRequest request) {
        InnerAccount account = innerAccountRepository.load(request.getAccountNo());
        AssertUtil.notNull(account, "账户不存在");
        account.setAccountName(request.getAccountName());
        account.setMemo(request.getMemo());
        innerAccountRepository.reStore(account);
    }

    @Override
    public void delete(String accountNo) {
        InnerAccount account = innerAccountRepository.load(accountNo);
        AssertUtil.notNull(account, "账户不存在");
        innerAccountRepository.delete(accountNo);
    }

    @Override
    public InnerAccountResponse detail(String accountNo) {
        InnerAccount account = innerAccountRepository.load(accountNo);
        return innerAccountConvertor.toResponse(account);
    }
}
