package com.anypluspay.admin.account.controller;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.request.OuterAccountRequest;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDO;
import com.anypluspay.account.infra.persistence.mapper.OuterAccountMapper;
import com.anypluspay.admin.account.convertor.OuterAccountConvertor;
import com.anypluspay.admin.account.dto.OuterAccountDto;
import com.anypluspay.admin.account.query.OuterAccountQuery;
import com.anypluspay.basis.web.controller.AbstractController;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.commons.validator.UpdateValidate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 外部账户
 *
 * @author wxj
 * 2024/12/30
 */
@RestController
@RequestMapping("/account/outer-account")
public class OuterAccountController extends AbstractController {

    @Autowired
    private OuterAccountConvertor convertor;

    @Autowired
    private OuterAccountMapper dalMapper;

    @Autowired
    private OuterAccountManagerFacade accountManagerFacade;

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<OuterAccountDto>> list(OuterAccountQuery query) {
        LambdaQueryWrapper<OuterAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getAccountNo())) {
            queryWrapper.eq(OuterAccountDO::getAccountNo, query.getAccountNo());
        } else {
            if (StrUtil.isNotBlank(query.getAccountName())) {
                queryWrapper.like(OuterAccountDO::getAccountName, query.getAccountName());
            }
            if (StrUtil.isNotBlank(query.getMemberId())) {
                queryWrapper.eq(OuterAccountDO::getMemberId, query.getMemberId());
            }
        }
        queryWrapper.orderByAsc(OuterAccountDO::getGmtCreate);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param accountNo 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<OuterAccountResponse> detail(@RequestParam String accountNo) {
        OuterAccountResponse outerAccountDto = accountManagerFacade.detail(accountNo);
        return ResponseResult.success(outerAccountDto);
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(UpdateValidate.class) OuterAccountRequest request) {
        accountManagerFacade.create(request);
        return ResponseResult.success();
    }

    /**
     * 修改
     *
     * @param request 修改请求对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) OuterAccountRequest request) {
        accountManagerFacade.update(request);
        return ResponseResult.success();
    }

    /**
     * 变更账户禁止状态
     *
     * @param accountNo 账户编号
     * @param denyStatusCode 账户状态编码
     */
    @GetMapping("/change-deny-status")
    public ResponseResult<String> changeDenyStatus(@RequestParam String accountNo, @RequestParam String denyStatusCode) {
        accountManagerFacade.changeDenyStatus(accountNo, denyStatusCode);
        return ResponseResult.success();
    }
}
