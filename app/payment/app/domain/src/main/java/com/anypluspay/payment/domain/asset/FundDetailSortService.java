package com.anypluspay.payment.domain.asset;

import com.anypluspay.payment.types.asset.AssetTypeCategory;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资金排序
 *
 * @author wxj
 * 2025/4/17
 */

@Component
public class FundDetailSortService {

    /**
     * 付款方资金排序
     *
     * @param fundDetails
     */
    public void payerSort(List<FundDetail> fundDetails) {
        fundDetails.sort((o1, o2) -> {
            if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                    && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
            ) {
                return -1;
            } else if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                    && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
            ) {
                return 1;
            }
            return 0;
        });
    }

    /**
     * 收款方资金排序
     *
     * @param fundDetails
     */
    public void payeeSort(List<FundDetail> fundDetails) {
        fundDetails.sort((o1, o2) -> {
            if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                    && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
            ) {
                return -1;
            } else if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                    && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
            ) {
                return 1;
            }
            return 0;
        });
    }

}
