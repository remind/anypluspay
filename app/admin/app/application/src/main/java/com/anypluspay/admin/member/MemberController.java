package com.anypluspay.admin.member;

import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.member.convertor.MemberConvertor;
import com.anypluspay.admin.member.query.MemberQuery;
import com.anypluspay.admin.member.response.MemberResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.infra.persistence.dataobject.MemberDO;
import com.anypluspay.payment.infra.persistence.mapper.MemberMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员管理
 *
 * @author wxj
 * 2025/5/27
 */
@RestController
@RequestMapping("/member/")
public class MemberController extends AbstractController {

    @Autowired
    private MemberConvertor convertor;

    @Autowired
    private MemberMapper dalMapper;

    /**
     * 列表
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<MemberResponse>> list(MemberQuery query) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getMemberId())) {
            queryWrapper.eq(MemberDO::getMemberId, query.getMemberId());
        }

        if (StringUtils.isNotBlank(query.getName())) {
            queryWrapper.like(MemberDO::getName, query.getName());
        }

        queryWrapper.orderByDesc(MemberDO::getGmtCreate);
        IPage<MemberDO> page = getIPage(query);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(page, queryWrapper)));
    }

}
