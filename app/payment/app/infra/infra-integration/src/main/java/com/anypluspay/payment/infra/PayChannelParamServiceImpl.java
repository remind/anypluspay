package com.anypluspay.payment.infra;

import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.payment.domain.PayChannelParamService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author wxj
 * 2025/5/23
 */
@Service
@Slf4j
public class PayChannelParamServiceImpl implements PayChannelParamService {

    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000) // 最大容量
            .expireAfterWrite(30, TimeUnit.MINUTES) // 过期时间
            .build(new CacheLoader<>() {
                @Override
                public String load(String key) {
                    return null;
                }
            });

    @Override
    public void store(String payOrderId, String param) {
        AssertUtil.notNull(payOrderId, "payOrderId不能为空");
        cache.put(payOrderId, param);
    }

    @Override
    public String get(String payOrderId) {
        AssertUtil.notNull(payOrderId, "payOrderId不能为空");
        try {
            return cache.get(payOrderId);
        } catch (ExecutionException e) {
            log.error("获取支付参数异常,payOrderId=" + payOrderId, e);
            return null;
        }
    }
}
