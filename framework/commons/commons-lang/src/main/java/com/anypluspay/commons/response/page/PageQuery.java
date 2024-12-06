package com.anypluspay.commons.response.page;

import lombok.Data;

/**
 * @author wxj
 * 2024/1/13
 */
@Data
public class PageQuery {

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 页码
     */
    private long page = 1;

    public long getOffset() {
        long current = getPage();
        if (current <= 1L) {
            return 0L;
        }
        return Math.max((current - 1) * getPageSize(), 0L);
    }

}
