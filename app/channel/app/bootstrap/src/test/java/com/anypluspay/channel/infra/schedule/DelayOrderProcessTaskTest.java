package com.anypluspay.channel.infra.schedule;

import com.anypluspay.channel.application.FundOutBaseTest;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.infra.persistence.mapper.TaskLoaderMapper;
import com.anypluspay.channel.types.order.BizOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * @author wxj
 * 2024/12/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DelayOrderProcessTaskTest extends FundOutBaseTest {

    @Autowired
    private DelayOrderProcessTask delayOrderProcessTask;

    @SpyBean
    protected TaskLoaderMapper taskLoaderMapper;

    @Test
    public void executeTaskSuccess() {
        FundResult fundResult = createFundOutOrder();
        BaseBizOrder bizOrder = bizOrderRepository.load(fundResult.getOrderId());
        Assert.assertEquals(BizOrderStatus.PROCESSING, bizOrder.getStatus());
        AtomicReference<Integer> count = new AtomicReference<>(0);
        doAnswer(invocation -> {
            count.getAndSet(count.get() + 1);
            if (count.get() == 1) {
                return List.of(bizOrder.getInstOrderId()); // 第一次调用返回 mock 结果
            } else {
                return null; // 后续调用返回null，这里最好是执行原始的查询逻辑，但是invocation.callRealMethod()会报错
            }
        }).when(taskLoaderMapper).loadDelayInstOrder(any());
        delayOrderProcessTask.executeTask();
        BaseBizOrder bizOrder1 = bizOrderRepository.load(fundResult.getOrderId());
        Assert.assertEquals(BizOrderStatus.SUCCESS, bizOrder1.getStatus());
    }
}
