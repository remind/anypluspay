package com.anypluspay.payment.facade.acquiring.create;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.application.TradeBuilder;
import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.biz.TradeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 交易创建构造器
 * @author wxj
 * 2025/5/17
 */
@Service
@Slf4j
public class AcquiringCreateBuilder extends TradeBuilder {

    /**
     * 构造交易订单
     * @param request
     * @return
     */
    public AcquiringOrder buildTradeOrder(AcquiringCreateRequest request) {
        AcquiringOrder acquiringOrder = new AcquiringOrder();
        acquiringOrder.setTradeId(idGeneratorService.genPaymentId(request.getPartnerId(), IdType.TRADE_ORDER_ID));
        acquiringOrder.setPartnerId(request.getPartnerId());
        acquiringOrder.setOutTradeNo(request.getOutTradeNo());
        acquiringOrder.setTradeType(EnumUtil.getByCode(TradeType.class, request.getTradeType()));
        acquiringOrder.setAmount(request.getAmount());
        acquiringOrder.setSubject(request.getSubject());
        acquiringOrder.setPayeeId(request.getPayeeId());
        acquiringOrder.setPayeeAccountNo(request.getPayeeAccountNo());
        acquiringOrder.setPayerId(request.getPayerId());
        try {
            acquiringOrder.setExtension(new Extension(request.getExtension()));
        } catch (Exception e) {
            log.error("扩展信息转化异常", e);
            throw new BizException("扩展信息无法转为json");
        }
        acquiringOrder.setStatus(AcquiringOrderStatus.INIT);
        acquiringOrder.setGmtExpire(getExpireTime(request.getExpireMinutes()));
        return acquiringOrder;
    }

    /**
     * 获取过期时间
     * @param expireMinutes
     * @return
     */
    private LocalDateTime getExpireTime(Integer expireMinutes) {
        if (expireMinutes == null) {
            expireMinutes = PaymentConstants.DEFAULT_EXPIRE_MINUTES;
        }
        return LocalDateTime.now().plusMinutes(expireMinutes);
    }

    /**
     * 构造交易创建响应
     * @param acquiringOrder
     * @return
     */
    public AcquiringCreateResponse buildResponse(AcquiringOrder acquiringOrder) {
        AcquiringCreateResponse response = new AcquiringCreateResponse();
        response.setSuccess(true);
        response.setTradeId(acquiringOrder.getTradeId());
        response.setPartnerId(acquiringOrder.getPartnerId());
        response.setOutTradeNo(acquiringOrder.getOutTradeNo());
        return response;
    }

    /**
     * 构造交易创建响应
     * @param request
     * @param e
     * @return
     */
    public AcquiringCreateResponse buildResponse(AcquiringCreateRequest request, Exception e) {
        AcquiringCreateResponse response = new AcquiringCreateResponse();
        response.setPartnerId(request.getPartnerId());
        response.setOutTradeNo(request.getOutTradeNo());
        BaseResult.fillExceptionResult(response, e);
        return response;
    }

}
