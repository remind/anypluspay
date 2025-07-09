package com.anypluspay.payment.facade.member;

import com.anypluspay.payment.facade.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 会员银行卡
 * @author wxj
 * 2025/7/8
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "MemberQueryFacade")
public interface MemberBankCardFacade {

    String PREFIX = "/member-bank-card";

    /**
     * 查询会员的银行卡列表
     *
     * @param memberId
     * @return
     */
    @GetMapping(PREFIX + "/query-by-member-id")
    List<MemberBankCardResponse> queryByMemberId(@RequestParam String memberId);

    /**
     * 添加银行卡
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/add")
    MemberBankCardResponse add(MemberBankCardRequest request);

    /**
     * 修改银行卡
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/update")
    MemberBankCardResponse update(MemberBankCardRequest request);

    /**
     * 删除银行卡
     *
     * @param id
     * @return
     */
    @GetMapping(PREFIX + "/delete")
    void delete(@RequestParam Long id);

}
