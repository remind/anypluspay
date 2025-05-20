package com.anypluspay.payment.domain.process;

import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.payment.types.status.OrderStatus;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 抽象支付指令
 * @author wxj
 * 2024/1/15
 */
@Data
public abstract class BasePayProcess<T extends OrderStatus> extends Entity {

    /**
     * 支付请求流水号
     */
    private String requestId;

    /**
     * 支付单号
     */
    private String paymentId;

    /**
     * 指令ID
     */
    private String processId;

    /**
     * 订单金额
     */
    private Money amount;

    /**
     * 发起人会员ID
     */
    private String memberId;

    /**
     * 订单状态
     */
    private T status;

    /**
     * 订单扩展信息
     */
    private String extension;

    /**
     * 执行结果码
     */
    private String resultCode;

    /**
     * 执行结果信息
     */
    private String resultMsg;

    /**
     * 收款方资金详情
     */
    private List<FundDetail> payeeDetails = new ArrayList<>();

    /**
     * 付款方资金详情
     */
    private List<FundDetail> payerDetails = new ArrayList<>();

    public void addPayeeFundDetail(FundDetail fundDetail) {
        AssertUtil.isTrue(fundDetail.getBelongTo() == BelongTo.PAYEE, "收款方资金详情 belongTo 为 PAYEE");
        this.payeeDetails.add(fundDetail);
    }

    public void addPayerFundDetail(FundDetail fundDetail) {
        AssertUtil.isTrue(fundDetail.getBelongTo() == BelongTo.PAYER, "付款方资金详情 belongTo 为 PAYER");
        this.payerDetails.add(fundDetail);
    }

    public FundDetail getFundDetail(String fundDetailId) {
        FundDetail fundDetail = payeeDetails.stream().filter(fundDetail1 -> fundDetail1.getDetailId().equals(fundDetailId)).findFirst().orElse(null);
        return fundDetail != null ? fundDetail : payerDetails.stream().filter(fundDetail1 -> fundDetail1.getDetailId().equals(fundDetailId)).findFirst().orElse(null);
    }

}
