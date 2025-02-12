package com.anypluspay.payment.domain.asset;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.types.asset.AssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 交换执行器
 *
 * @author wxj
 * 2025/2/12
 */
@Service
public class FluxExecutor {

    @Autowired
    private Map<String, AssetFluxExecutor> executorMap;

    /**
     * 执行交换
     *
     * @param fluxOrder       交换单
     * @param fluxInstruction 交换指令
     * @return 交换结果
     */
    public FluxResult execute(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        AssetFluxExecutor executor = getExecutor(fluxInstruction.getAssetInfo().getAssetType());
        return switch (fluxInstruction.getFundAction()) {
            case INCREASE -> executor.increase(fluxOrder, fluxInstruction);
            case DECREASE -> executor.decrease(fluxOrder, fluxInstruction);
            case FREEZE -> executor.freeze(fluxOrder, fluxInstruction);
            case UNFREEZE -> executor.unfreeze(fluxOrder, fluxInstruction);
        };
    }

    private AssetFluxExecutor getExecutor(AssetType assetType) {
        AssetFluxExecutor executor = executorMap.get(assetType.getAssetTypeCategory().getCode() + AssetFluxExecutor.EXECUTOR_SUFFIX);
        Assert.notNull(executor, "找不到对应的执行器");
        return executor;
    }
}
