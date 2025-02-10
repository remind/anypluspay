package com.anypluspay.payment.domain.asset.factory;

import com.anypluspay.payment.domain.asset.FluxInstructionExecutor;
import com.anypluspay.payment.types.asset.AssetTypeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 资金交换工厂类
 *
 * @author wxj
 * 2024/1/27
 */
@Service
public class AssetFluxFactory {

    @Autowired
    private Map<String, FluxInstructionExecutor> executorMap;

    public FluxInstructionExecutor getFluxInstructionExecutor(AssetTypeCategory assetTypeCategory) {
        FluxInstructionExecutor executor = executorMap.get(assetTypeCategory.getCode() + "_FluxInstructExecutor");
        Assert.notNull(executor, "找不到对应的执行器");
        return executor;
    }
}
