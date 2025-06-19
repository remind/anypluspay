package com.anypluspay.payment.domain.asset.external;

import com.anypluspay.payment.domain.asset.AssetFluxExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.asset.external.apply.ChannelFundInService;
import com.anypluspay.payment.domain.asset.external.apply.ChannelFundOutService;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.flux.FluxProcessDirection;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.anypluspay.payment.domain.asset.AssetFluxExecutor.EXECUTOR_SUFFIX;

/**
 * 外部渠道指令执行器
 *
 * @author wxj
 * 2025/2/12
 */
@Component("EXTERNAL" + EXECUTOR_SUFFIX)
public class ExternalExecutor implements AssetFluxExecutor {

    @Autowired
    private IdGeneratorService idGeneratorService;


    @Autowired
    private ChannelFundInService channelFundInService;

    @Autowired
    private ChannelFundOutService channelFundOutService;

    @Override
    public FluxResult increase(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        IdType tradIdType = idGeneratorService.getIdType(fluxOrder.getTradeId());
        if (tradIdType == IdType.WITHDRAW_ORDER_ID) {
            return channelFundOutService.apply(fluxOrder, fluxProcess);
        } else {
            if (fluxOrder.getPayType() == PayOrderType.REFUND ||
                    (fluxOrder.getPayType() == PayOrderType.PAY && fluxProcess.getDirection() == FluxProcessDirection.REVOKE)) {
                return channelFundInService.refund(fluxOrder, fluxProcess);
            }
        }
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult decrease(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        return channelFundInService.apply(fluxOrder, fluxProcess);
    }

    @Override
    public FluxResult freeze(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult unfreeze(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        throw new UnsupportedOperationException("不支持的指令");
    }

}
