package com.anypluspay.payment.domain.asset;

import com.anypluspay.payment.domain.flux.FluxProcess;
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
     * @param fluxProcess 交换指令
     * @return 交换结果
     */
    public FluxResult execute(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        AssetFluxExecutor executor = getExecutor(fluxProcess.getAssetInfo().getAssetType());
        return switch (fluxProcess.getFundAction()) {
            case INCREASE -> executor.increase(fluxOrder, fluxProcess);
            case DECREASE -> executor.decrease(fluxOrder, fluxProcess);
            case FREEZE -> executor.freeze(fluxOrder, fluxProcess);
            case UNFREEZE -> executor.unfreeze(fluxOrder, fluxProcess);
        };
    }

    private AssetFluxExecutor getExecutor(AssetType assetType) {
        AssetFluxExecutor executor = executorMap.get(assetType.getAssetTypeCategory().getCode() + AssetFluxExecutor.EXECUTOR_SUFFIX);
        Assert.notNull(executor, "找不到对应的执行器");
        return executor;
    }
}
