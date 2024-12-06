package com.anypluspay.admin.web.controller.order;

import com.anypluspay.admin.dao.mapper.FundInOrderQueryMapper;
import com.anypluspay.admin.model.order.FundInOrderDetailDto;
import com.anypluspay.admin.model.query.Order.FundInOrderQuery;
import com.anypluspay.admin.web.controller.AbstractController;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入款订单查询
 * @author wxj
 * 2024/11/13
 */
@RestController
@RequestMapping("/fund-in-order")
public class FundInOrderController extends AbstractController {

    @Autowired
    private FundInOrderQueryMapper fundInOrderQueryMapper;

    /**
     * 列表分页查询
     * @param query 查询参数
     * @return  查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<FundInOrderDetailDto>> list(FundInOrderQuery query) {
        IPage<FundInOrderDetailDto> page = fundInOrderQueryMapper.pageQuery(query, getIPage(query));
        return ResponseResult.success(toPageResult(page));
    }
}
