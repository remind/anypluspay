package com.anypluspay.channel.infra.persistence.channel;

import com.anypluspay.commons.response.page.PageQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author wxj
 * 2024/11/5
 */
public abstract class AbstractRepository {

    protected <T> IPage<T> getIPage(PageQuery pageQuery) {
        return new Page<>(pageQuery.getPage(), pageQuery.getPageSize());
    }
}
