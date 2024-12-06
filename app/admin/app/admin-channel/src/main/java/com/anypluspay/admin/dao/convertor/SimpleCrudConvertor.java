package com.anypluspay.admin.dao.convertor;

import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单CRUD 转换器
 *
 * @author wxj
 * 2024/12/5
 */
public interface SimpleCrudConvertor<DtoType, RequestType, DoType> extends BaseExpressionConvertor {

    /**
     * request 转换为DO，用于新增修改
     *
     * @param request 请求对象
     * @return DO
     */
    DoType requestToDo(RequestType request);

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
