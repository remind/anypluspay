package com.anypluspay.admin.account.controller;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.account.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.account.infra.persistence.mapper.AccountTitleMapper;
import com.anypluspay.admin.account.convertor.AccountTitleConvertor;
import com.anypluspay.admin.account.dto.AccountTitleDto;
import com.anypluspay.admin.account.query.AccountTitleQuery;
import com.anypluspay.basis.web.controller.AbstractController;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 科目
 *
 * @author wxj
 * 2024/12/24
 */
@RestController
@RequestMapping("/account/account-title")
public class AccountTitleController extends AbstractController {

    @Autowired
    private AccountTitleMapper dalMapper;

    @Autowired
    private AccountTitleConvertor convertor;

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<AccountTitleDto>> list(AccountTitleQuery query) {
        LambdaQueryWrapper<AccountTitleDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getCode())) {
            queryWrapper.like(AccountTitleDO::getCode, query.getCode());
        }
        if (StrUtil.isNotBlank(query.getName())) {
            queryWrapper.like(AccountTitleDO::getName, query.getName());
        }
        if (query.getEnable() != null) {
            queryWrapper.eq(AccountTitleDO::getEnable, query.getEnable());
        }
        queryWrapper.orderByDesc(AccountTitleDO::getGmtCreate);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }
}
