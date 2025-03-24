package com.anypluspay.admin.channel.web.controller.example;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.anypluspay.admin.channel.web.controller.example.convertor.ExampleConvertor;
import com.anypluspay.admin.channel.web.controller.example.request.ExampleRefundRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 支付测试
 * @author wxj
 * 2024/12/7
 */
@RestController
@RequestMapping("/pay-example")
public class PayExampleController {

    @Autowired
    private FundInFacade fundInFacade;

    @Autowired
    private RefundFacade refundFacade;

    @Autowired
    private ExampleConvertor exampleConvertor;

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
    public ResponseResult<FundResult> refund(@RequestBody ExampleRefundRequest exampleRefundRequest) {
        RefundRequest request = exampleConvertor.toRefundRequest(exampleRefundRequest);
        request.setRequestId(UUID.fastUUID().toString(true));
        return ResponseResult.success(refundFacade.apply(request));
    }
}
