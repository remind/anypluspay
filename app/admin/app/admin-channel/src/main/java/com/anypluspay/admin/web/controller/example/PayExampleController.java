package com.anypluspay.admin.web.controller.example;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.commons.lang.utils.ExtUtil;
import com.anypluspay.commons.response.ResponseResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付测试
 * @author wxj
 * 2024/12/7
 */
@RestController
@RequestMapping("/pay-example")
public class PayExampleController {

    @DubboReference
    private FundInFacade fundInFacade;

    @DubboReference
    private RefundFacade refundFacade;

    @PostMapping("/pay")
    public ResponseResult<FundResult> pay(@RequestBody FundInRequest request) {
        request.setRequestId(UUID.fastUUID().toString(true));
        ExtUtil.addValue(request.getInstExtra(), ExtKey.TEST_FLAG, JSONUtil.toJsonStr(new TestFlag(TestConstants.S, null)));
        return ResponseResult.success(fundInFacade.apply(request));
    }

    @PostMapping("/notify")
    public ResponseResult<FundResult> notify(RefundRequest request) {
        return ResponseResult.success(refundFacade.apply(request));
    }

    @PostMapping("/refund")
    public ResponseResult<FundResult> refund(RefundRequest request) {
        return ResponseResult.success(refundFacade.apply(request));
    }
}
