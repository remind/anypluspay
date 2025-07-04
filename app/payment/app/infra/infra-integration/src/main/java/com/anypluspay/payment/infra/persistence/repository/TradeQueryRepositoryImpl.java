package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.commons.response.page.PageQuery;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.domain.repository.TradeQueryRepository;
import com.anypluspay.payment.infra.persistence.convertor.query.DepositQueryDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.DepositOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.DepositOrderMapper;
import com.anypluspay.payment.infra.persistence.mapper.TradeBillQueryMapper;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2025/7/1
 */
@Repository
public class TradeQueryRepositoryImpl implements TradeQueryRepository {

    @Autowired
    private TradeBillQueryMapper tradeBillQueryMapper;

    @Autowired
    private DepositOrderMapper depositOrderMapper;

    @Autowired
    private DepositQueryDalConvertor depositQueryDalConvertor;

    @Override
    public PageResult<TradeBillDto> queryBill(TradeBillQuery query) {
        IPage page = getIPage(query);
        return toPageResult(tradeBillQueryMapper.queryBill(query, page));
    }

    @Override
    public PageResult<DepositOrderResponse> pageQueryDeposit(DepositOrderQuery query) {
        LambdaQueryWrapper<DepositOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getTradeId())) {
            queryWrapper.eq(DepositOrderDO::getTradeId, query.getTradeId());
        }
        queryWrapper.orderByDesc(DepositOrderDO::getGmtCreate);
        IPage<DepositOrderDO> page = getIPage(query);
        return depositQueryDalConvertor.toEntity(depositOrderMapper.selectPage(page, queryWrapper));
    }

    protected <T> PageResult<T> toPageResult(IPage<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPage(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setItems(page.getRecords());
        return pageResult;
    }

    protected <T> IPage<T> getIPage(PageQuery pageQuery) {
        return new Page<>(pageQuery.getPage(), pageQuery.getPageSize());
    }
}
