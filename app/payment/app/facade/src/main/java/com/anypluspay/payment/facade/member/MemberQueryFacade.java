package com.anypluspay.payment.facade.member;

import com.anypluspay.commons.enums.EnumObject;
import com.anypluspay.payment.facade.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wxj
 * 2025/7/8
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "MemberQueryFacade")
public interface MemberQueryFacade {

    String PREFIX = "/member-query";

    /**
     * 查询会员的银行卡列表
     *
     * @param memberId
     * @return
     */
    @GetMapping(PREFIX + "/query-bank-card")
    List<MemberBankCardResponse> queryBankCard(@RequestParam String memberId);

    /**
     * 添加银行卡
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/add-bank-card")
    MemberBankCardResponse addBankCard(MemberBankCardRequest request);

    /**
     * 修改银行卡
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/update-bank-card")
    MemberBankCardResponse updateBankCard(MemberBankCardRequest request);

    /**
     * 删除银行卡
     *
     * @param id
     * @return
     */
    @GetMapping(PREFIX + "/delete-bank-card")
    void deleteBankCard(@RequestParam Long id);

    /**
     * 查询银行列表
     *
     * @return
     */
    @GetMapping(PREFIX + "/query-bank-codes")
    List<EnumObject> queryBankCodes();

}
