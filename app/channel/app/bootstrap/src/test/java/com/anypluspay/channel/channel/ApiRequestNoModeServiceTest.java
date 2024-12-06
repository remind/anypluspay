package com.anypluspay.channel.channel;

import com.anypluspay.channel.domain.channel.api.ApiRequestNoMode;
import com.anypluspay.channel.domain.channel.api.service.ApiRequestNoModeService;
import com.anypluspay.channel.institution.BaseChannelTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/9/18
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ApiRequestNoModeServiceTest extends BaseChannelTest {

    @Autowired
    private ApiRequestNoModeService apiRequestNoModeService;

    @Test
    public void testSuccess() {
        ApiRequestNoMode apiRequestNoMode = new ApiRequestNoMode();
        apiRequestNoMode.setSeqName("seq_inst_wxpay");
        apiRequestNoMode.setGenPattern("f:50122|t:MMdd|f:022|s:8");
        String s = apiRequestNoModeService.generateRequestNo(apiRequestNoMode);
        log.info("ID:" + s);
        Assert.assertNotNull(s);
    }
}
