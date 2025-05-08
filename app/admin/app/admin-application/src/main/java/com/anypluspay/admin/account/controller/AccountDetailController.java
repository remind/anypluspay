package com.anypluspay.admin.account.controller;

import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDetailDO;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDetailDO;
import com.anypluspay.account.infra.persistence.mapper.InnerAccountDetailMapper;
import com.anypluspay.account.infra.persistence.mapper.OuterAccountDetailMapper;
import com.anypluspay.admin.account.convertor.InnerAccountDetailConvertor;
import com.anypluspay.admin.account.convertor.OuterAccountDetailConvertor;
import com.anypluspay.admin.account.query.AccountDetailQuery;
import com.anypluspay.admin.account.response.InnerAccountDetailResponse;
import com.anypluspay.admin.account.response.OuterAccountDetailResponse;
import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入账明细
 *
 * @author wxj
 * 2025/5/8
 */
@RestController
@RequestMapping("/account/account-detail")
public class AccountDetailController extends AbstractController {

    @Autowired
    private OuterAccountDetailMapper outerAccountDetailMapper;

    @Autowired
    private OuterAccountDetailConvertor outerAccountDetailConvertor;

    @Autowired
    private InnerAccountDetailMapper innerAccountDetailMapper;

    @Autowired
    private InnerAccountDetailConvertor innerAccountDetailConvertor;

    /**
     * 外部户明细列表
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/outer-list")
    public ResponseResult<PageResult<OuterAccountDetailResponse>> outerList(AccountDetailQuery query) {
        LambdaQueryWrapper<OuterAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getAccountNo())) {
            queryWrapper.eq(OuterAccountDetailDO::getAccountNo, query.getAccountNo());
        }

        if (query.getStartDate() != null) {
            queryWrapper.gt(OuterAccountDetailDO::getGmtCreate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            queryWrapper.le(OuterAccountDetailDO::getGmtCreate, query.getEndDate());
        }
        queryWrapper.orderByDesc(OuterAccountDetailDO::getGmtCreate);
        IPage<OuterAccountDetailDO> page = getIPage(query);
        return ResponseResult.success(outerAccountDetailConvertor.toEntity(outerAccountDetailMapper.selectPage(page, queryWrapper)));
    }

    /**
     * 内部户明细列表
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/inner-list")
    public ResponseResult<PageResult<InnerAccountDetailResponse>> innerList(AccountDetailQuery query) {
        LambdaQueryWrapper<InnerAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getAccountNo())) {
            queryWrapper.eq(InnerAccountDetailDO::getAccountNo, query.getAccountNo());
        }

        if (query.getStartDate() != null) {
            queryWrapper.gt(InnerAccountDetailDO::getGmtCreate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            queryWrapper.le(InnerAccountDetailDO::getGmtCreate, query.getEndDate());
        }
        queryWrapper.orderByDesc(InnerAccountDetailDO::getGmtCreate);
        IPage<InnerAccountDetailDO> page = getIPage(query);
        return ResponseResult.success(innerAccountDetailConvertor.toEntity(innerAccountDetailMapper.selectPage(page, queryWrapper)));
    }
}
