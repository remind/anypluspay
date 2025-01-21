package com.anypluspay.admin.account.mapper;

import com.anypluspay.admin.account.model.dto.TitleDailyDto;
import com.anypluspay.admin.account.model.query.TitleDailyQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科目汇总查询
 * @author wxj
 * 2025/1/21
 */
public interface TitleDailyQueryMapper {

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    List<TitleDailyDto> list(@Param("query") TitleDailyQuery query);
}
