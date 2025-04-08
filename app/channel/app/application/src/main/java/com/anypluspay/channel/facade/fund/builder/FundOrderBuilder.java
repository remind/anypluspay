package com.anypluspay.channel.facade.fund.builder;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.domain.IdType;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.FundOutOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.request.FundRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.enums.SystemCodeEnums;
import com.anypluspay.component.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 资金订单构造器
 * @author wxj
 * 2024/9/24
 */
@Component
public class FundOrderBuilder {

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private BizOrderRepository bizOrderRepository;

    /**
     * 构建资金流入订单
     * @param request
     * @return
     */
    public FundInOrder buildFundIn(FundInRequest request) {
        FundInOrder fundInOrder = new FundInOrder();
        fundInOrder.setPayInst(request.getPayInst());
        fundInOrder.setPayModel(request.getPayModel());
        fundInOrder.setAmount(request.getAmount());
        fillBizOrder(fundInOrder, request, IdType.BIZ_FUND_IN);
        return fundInOrder;
    }

    public FundOutOrder buildFundOut(FundOutRequest request) {
        FundOutOrder fundOutOrder = new FundOutOrder();
        fundOutOrder.setAmount(request.getAmount());
        fundOutOrder.setBankCode(request.getBankCode());
        fundOutOrder.setPayMethod(request.getPayMethod());
        fundOutOrder.setAccountNo(request.getAccountNo());
        fundOutOrder.setAccountName(request.getAccountName());
        fundOutOrder.setAccountType(request.getAccountType());
        fillBizOrder(fundOutOrder, request, IdType.BIZ_FUND_OUT);
        return fundOutOrder;
    }

    public RefundOrder buildRefund(RefundRequest request) {
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setRefundType(request.getRefundType());
        refundOrder.setAmount(request.getAmount());
        refundOrder.setReason(request.getReason());

        BaseBizOrder originOrder = null;
        if (StrUtil.isNotBlank(request.getOrigOrderId())) {
            originOrder = bizOrderRepository.load(request.getOrigOrderId());
        }
        if (StrUtil.isNotBlank(request.getOrigRequestId())) {
            originOrder = bizOrderRepository.load(request.getOrigOrderId());
        }
        Assert.notNull(originOrder, "原单不存在");
        Assert.isTrue(originOrder.getStatus() == BizOrderStatus.SUCCESS, "原单未成功");
        request.setMemberId(originOrder.getMemberId());

        refundOrder.setOrigOrderId(originOrder.getOrderId());
        refundOrder.setOrigRequestId(originOrder.getRequestId());
        fillBizOrder(refundOrder, request, IdType.BIZ_REFUND);
        return refundOrder;
    }

    private void fillBizOrder(BaseBizOrder bizOrder, FundRequest fundRequest, IdType idType) {
        bizOrder.setRequestId(fundRequest.getRequestId());
        bizOrder.setMemberId(fundRequest.getMemberId());
        bizOrder.setInstExt(fundRequest.getInstExt());
        bizOrder.setExtension(fundRequest.getExtension());
        bizOrder.setStatus(BizOrderStatus.PROCESSING);
        bizOrder.setOrderId(sequenceService.getId(fundRequest.getMemberId(), SystemCodeEnums.CHANNEL, idType));
    }
}
