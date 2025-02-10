package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.instant.InstantPayment;
import com.anypluspay.payment.domain.payorder.BasePayOrder;
import com.anypluspay.payment.domain.payorder.PayOrder;
import com.anypluspay.payment.domain.payorder.RefundOrder;
import com.anypluspay.payment.domain.repository.InstantPaymentRepository;
import com.anypluspay.payment.infra.persistence.repository.inner.BasePayOrderInnerRepository;
import com.anypluspay.payment.infra.persistence.repository.inner.FundDetailInnerRepository;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2024/1/17
 */
@Repository
public class InstantPaymentRepositoryImpl extends AbstractPaymentRepository implements InstantPaymentRepository {

    @Autowired
    private BasePayOrderInnerRepository basePayOrderInnerRepository;

    @Autowired
    private FundDetailInnerRepository fundDetailInnerRepository;

    @Override
    public void store(InstantPayment payment) {
        if(storePayment(payment)) {
            basePayOrderInnerRepository.store(payment.getPayOrder());
        }
    }

    @Override
    public InstantPayment load(String paymentId) {
        InstantPayment payment = (InstantPayment) loadPayment(paymentId, false);
        if (payment != null) {
            List<BasePayOrder> basePayOrders = basePayOrderInnerRepository.loadByPaymentId(paymentId);
            if (!CollectionUtils.isEmpty(basePayOrders)) {
                List<FundDetail> fundDetails = fundDetailInnerRepository.loadByPaymentId(paymentId);
                basePayOrders.forEach(basePayOrder -> {
                    if (basePayOrder instanceof PayOrder payOrder) {
                        payment.setPayOrder(payOrder);
                    } else if (basePayOrder instanceof RefundOrder refundOrder) {
                        payment.getRefundOrderList().add(refundOrder);
                    }
                    fillFundDetails(basePayOrder, fundDetails);
                });
            }
        }
        return payment;
    }

    private void fillFundDetails(BasePayOrder basePayOrder, List<FundDetail> fundDetails) {
        fundDetails.forEach(fundDetail -> {
            if (fundDetail.getOrderId().equals(basePayOrder.getOrderId())) {
                if (fundDetail.getBelongTo() == BelongTo.PAYEE) {
                    basePayOrder.addPayeeFundDetail(fundDetail);
                } else if (fundDetail.getBelongTo() == BelongTo.PAYER) {
                    basePayOrder.addPayerFundDetail(fundDetail);
                }
            }
        });
    }
 }
