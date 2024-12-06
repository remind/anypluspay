package com.anypluspay.account.domain.detail;

import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.GlobalResultCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2023/12/16
 */
@Data
public class OuterAccountDetail extends AccountDetail {

    /**
     * 入账前余额
     */
    private Money beforeBalance = new Money();

    /**
     * 入账后余额
     */
    private Money afterBalance = new Money();

    /**
     * 加减方向
     */
    private IODirection ioDirection;

    /**
     * 子户明细
     */
    private List<OuterSubAccountDetail> outerSubAccountDetails = new ArrayList<>();

    public void addSubDetail(String fundType, Money amount) {
        AssertUtil.isTrue(outerSubAccountDetails.stream().filter(
                        outerSubAccountDetail -> outerSubAccountDetail.getFundType().equals(fundType))
                .findAny().isEmpty(), GlobalResultCode.FAIL, "已经存在对应的明细");
        OuterSubAccountDetail outerSubAccountDetail = new OuterSubAccountDetail();
        outerSubAccountDetail.setRequestNo(this.getRequestNo());
        outerSubAccountDetail.setVoucherNo(this.getVoucherNo());
        outerSubAccountDetail.setAccountNo(this.getAccountNo());
        outerSubAccountDetail.setFundType(fundType);
        outerSubAccountDetail.setAmount(amount);
        outerSubAccountDetails.add(outerSubAccountDetail);
    }

}
