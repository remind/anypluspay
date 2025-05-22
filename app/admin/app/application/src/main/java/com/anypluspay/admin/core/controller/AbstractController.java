package com.anypluspay.admin.core.controller;

import com.anypluspay.commons.response.page.PageQuery;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author wxj
 * 2024/12/3
 */
public abstract class AbstractController {

    protected <T> IPage<T> getIPage(PageQuery pageQuery) {
        return new Page<>(pageQuery.getPage(), pageQuery.getPageSize());
    }

    protected <T> PageResult<T> toPageResult(IPage<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPage(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setItems(page.getRecords());
        return pageResult;
    }
}
