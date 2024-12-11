package com.anypluspay.admin.web.controller.order;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.dao.convertor.order.InstOrderConvertor;
import com.anypluspay.admin.model.order.InstOrderDto;
import com.anypluspay.admin.model.query.Order.InstOrderQuery;
import com.anypluspay.admin.web.controller.AbstractController;
import com.anypluspay.channel.facade.OrderQueryFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.InstOrderMapper;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2024/11/22
 */
@RestController
@RequestMapping("/inst-order")
public class InstOrderController extends AbstractController {

    @Autowired
    private InstOrderConvertor instOrderConvertor;

    @Autowired
    private InstOrderMapper instOrderMapper;

    @DubboReference
    private OrderQueryFacade orderQueryFacade;

    /**
     * 列表分页查询
     *
     * @param query
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<InstOrderDto>> list(InstOrderQuery query) {
        LambdaQueryWrapper<InstOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getInstOrderId())) {
            queryWrapper.eq(InstOrderDO::getInstOrderId, query.getInstOrderId());
        }
        if (StrUtil.isNotBlank(query.getStatus())) {
            queryWrapper.eq(InstOrderDO::getStatus, query.getStatus());
        }
        if (query.getGmtCreateStart() != null) {
            queryWrapper.ge(InstOrderDO::getGmtCreate, query.getGmtCreateStart());
        }
        if (query.getGmtCreateEnd() != null) {
            queryWrapper.le(InstOrderDO::getGmtCreate, query.getGmtCreateEnd());
        }
        IPage<InstOrderDO> page = instOrderMapper.selectPage(getIPage(query), queryWrapper);
        return ResponseResult.success(instOrderConvertor.toDto(page));
    }

    /**
     * 调用渠道查询
     * @param instOrderId   机构订单号
     * @return
     */
    @GetMapping("/inst-query")
    public ResponseResult<FundResult> instQuery(String instOrderId) {
        return ResponseResult.success(orderQueryFacade.queryByInstOrderId(instOrderId, true));
    }
}
