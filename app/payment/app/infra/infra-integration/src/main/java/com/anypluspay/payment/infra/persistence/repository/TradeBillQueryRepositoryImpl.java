package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.commons.response.page.PageQuery;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.domain.repository.TradeBillQueryRepository;
import com.anypluspay.payment.infra.persistence.mapper.TradeBillQueryMapper;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2025/7/1
 */
@Repository
public class TradeBillQueryRepositoryImpl implements TradeBillQueryRepository {

    @Autowired
    private TradeBillQueryMapper tradeBillQueryMapper;

    @Override
    public PageResult<TradeBillDto> queryBill(TradeBillQuery query) {
        IPage page = getIPage(query);
        return toPageResult(tradeBillQueryMapper.queryBill(query, page));
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
