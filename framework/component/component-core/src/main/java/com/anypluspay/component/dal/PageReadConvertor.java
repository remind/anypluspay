package com.anypluspay.component.dal;

import com.anypluspay.commons.convertor.ReadConvertor;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author wxj
 * 2025/5/8
 */
public interface PageReadConvertor<EntityType, DoType> extends ReadConvertor<EntityType, DoType> {

    default PageResult<EntityType> toEntity(IPage<DoType> page) {
        PageResult<EntityType> pageResult = new PageResult<>();
        pageResult.setPage(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setItems(toEntity(page.getRecords()));
        return pageResult;
    }

}
