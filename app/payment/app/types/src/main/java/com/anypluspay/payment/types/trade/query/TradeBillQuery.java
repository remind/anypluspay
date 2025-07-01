package com.anypluspay.payment.types.trade.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * 交易查询请求
 *
 * @author wxj
 * 2025/6/29
 */
@Data
public class TradeBillQuery extends PageQuery {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 账单类型
     */
    private List<Integer> billType;

}
