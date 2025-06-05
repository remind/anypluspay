package com.anypluspay.channelgateway;

/**
 * @author wxj
 * 2025/6/4
 */
public interface ApiParamRefreshListener<T> {

    void onRefresh(String id, T object);
}
