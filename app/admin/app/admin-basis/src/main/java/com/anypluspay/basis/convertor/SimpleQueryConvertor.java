package com.anypluspay.basis.convertor;

import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单查询转换器
 * @author wxj
 * 2024/12/11
 */
public interface SimpleQueryConvertor<DtoType, DoType> extends BaseExpressionConvertor {

    /**
     * DO 转换为DTO，用于查询
     *
     * @param doObject DO
     * @return DTO
     */
    DtoType toDto(DoType doObject);

    default List<DtoType> toDto(List<DoType> doObjects) {
        List<DtoType> dtoObjects = new ArrayList<>();
        if (doObjects != null) {
            doObjects.forEach(doObject -> dtoObjects.add(toDto(doObject)));
        }
        return dtoObjects;
    }

    default PageResult<DtoType> toDto(IPage<DoType> page) {
        PageResult<DtoType> pageResult = new PageResult<>();
        pageResult.setPage(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setItems(toDto(page.getRecords()));

        return pageResult;
    }
}
