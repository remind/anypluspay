package com.anypluspay.commons.response.page;

import lombok.Data;

import java.util.List;

/**
 * 分页结果
 * @author wxj
 * 2024/1/12
 */
@Data
public class PageResult<T> {

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 页码
     */
    private long page = 1;

    /**
     * 总条数
     */
    private long total;

    /**
     * 数据
     */
    private List<T> items;
}
