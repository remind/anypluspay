package com.anypluspay.account.facade.accounting;


import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.domain.repository.AccountTitleRepository;
import com.anypluspay.account.facade.accounting.builder.AccountTitleBuilder;
import com.anypluspay.account.facade.accounting.dto.AccountTitleRequest;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 科目管理
 *
 * @author wxj
 * 2023/12/16
 */
@Slf4j
@RestController
@Validated
public class AccountTitleManagerFacadeImpl implements AccountTitleManagerFacade {

    @Autowired
    private AccountTitleBuilder accountTitleBuilder;

    @Autowired
    private AccountTitleRepository accountTitleRepository;

    @Override
    public boolean createAccountTitle(AccountTitleRequest request) {
        String parentCode = request.getParentCode();
        AccountTitle parentAccountTitle = null;
        if (StringUtils.isNotBlank(parentCode)) {
            parentAccountTitle = accountTitleRepository.load(parentCode);
            AssertUtil.notNull(parentAccountTitle, "父科目为空");
        }
        AccountTitle accountTitle = accountTitleBuilder.build(request, parentAccountTitle);
        try {
            accountTitleRepository.store(accountTitle);
        } catch (DuplicateKeyException e) {
            throw new BizException("科目代码已经存在");
        }
        return true;
    }

    @Override
    public boolean updateAccountTitle(AccountTitleRequest request) {
        AccountTitle accountTitle = accountTitleRepository.load(request.getCode());
        accountTitle.setName(request.getName());
        accountTitle.setMemo(request.getMemo());
        accountTitle.setEnable(request.getEnable());
        accountTitleRepository.reStore(accountTitle);
        return true;
    }

    @Override
    public void deleteAccountTitle(String code) {
        AccountTitle accountTitle = accountTitleRepository.load(code);
        AssertUtil.notNull(accountTitle, "科目不存在");
        accountTitleRepository.delete(code);
    }
}
