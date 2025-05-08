package com.anypluspay.admin.account.controller;

import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDetailDO;
import com.anypluspay.account.infra.persistence.mapper.OuterAccountDetailMapper;
import com.anypluspay.admin.account.convertor.OuterAccountDetailConvertor;
import com.anypluspay.admin.account.query.OuterAccountDetailQuery;
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
 * 外部户明细
 *
 * @author wxj
 * 2025/5/8
 */
@RestController
@RequestMapping("/account/outer-account-detail")
public class OuterAccountDetailController extends AbstractController {

    @Autowired
    private OuterAccountDetailMapper outerAccountDetailMapper;

    @Autowired
    private OuterAccountDetailConvertor outerAccountDetailConvertor;

    /**
     * 列表分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<OuterAccountDetailResponse>> list(OuterAccountDetailQuery query) {
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
}
