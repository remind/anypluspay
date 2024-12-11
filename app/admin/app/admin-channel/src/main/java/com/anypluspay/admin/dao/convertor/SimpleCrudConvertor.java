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
public interface SimpleCrudConvertor<DtoType, RequestType, DoType> extends SimpleQueryConvertor<DtoType,DoType> {

    /**
     * request 转换为DO，用于新增修改
     *
     * @param request 请求对象
     * @return DO
     */
    DoType requestToDo(RequestType request);

}
