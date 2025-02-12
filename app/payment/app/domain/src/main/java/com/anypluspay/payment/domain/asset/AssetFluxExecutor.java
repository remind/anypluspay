package com.anypluspay.payment.domain.asset;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;

/**
 * 资产交换执行器
 * @author wxj
 * 2025/2/11
 */
public interface AssetFluxExecutor {

    String EXECUTOR_SUFFIX = "_FluxExecutor";

    /**
     * 增加资产
     *
     * @param fluxOrder 交换单
     * @param fluxInstruction   交换指令
     * @return  交换结果
     */
    FluxResult increase(FluxOrder fluxOrder, FluxInstruction fluxInstruction);

    /**
     * 减少资产
     *
     * @param fluxOrder 交换单
     * @param fluxInstruction   交换指令
     * @return  交换结果
     */
    FluxResult decrease(FluxOrder fluxOrder, FluxInstruction fluxInstruction);

    /**
     * 冻结资产
     *
     * @param fluxOrder 交换单
     * @param fluxInstruction   交换指令
     * @return  交换结果
     */
    FluxResult freeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction);

    /**
     * 解冻资产
     *
     * @param fluxOrder 交换单
     * @param fluxInstruction   交换指令
     * @return  交换结果
     */
    FluxResult unfreeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction);
}
