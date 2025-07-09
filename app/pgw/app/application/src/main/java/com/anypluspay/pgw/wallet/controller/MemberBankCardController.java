package com.anypluspay.pgw.wallet.controller;

import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import com.anypluspay.payment.facade.member.MemberBankCardFacade;
import com.anypluspay.payment.facade.member.MemberBankCardRequest;
import com.anypluspay.payment.facade.member.MemberBankCardResponse;
import com.anypluspay.pgw.wallet.AbstractWalletController;
import com.anypluspay.pgw.wallet.request.BankCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员银行卡
 *
 * @author wxj
 * 2025/7/9
 */
@RestController
@RequestMapping("/wallet/member-bank-card")
public class MemberBankCardController extends AbstractWalletController {

    @Autowired
    private MemberBankCardFacade memberBankCardFacade;

    /**
     * 查询会员的银行卡列表
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<List<MemberBankCardResponse>> list() {
        return ResponseResult.success(memberBankCardFacade.queryByMemberId(getLoginMember().getMemberId()));
    }

    /**
     * 添加银行卡
     *
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<MemberBankCardResponse> add(@Validated(AddValidate.class) BankCardRequest request) {
        return ResponseResult.success(memberBankCardFacade.add(toMemberBankCardRequest(request)));
    }

    /**
     * 修改银行卡
     *
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<MemberBankCardResponse> update(@Validated(UpdateValidate.class) BankCardRequest request) {
        return ResponseResult.success(memberBankCardFacade.update(toMemberBankCardRequest(request)));
    }

    /**
     * 删除银行卡
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public ResponseResult<String> delete(@RequestParam Long id) {
        memberBankCardFacade.delete(id);
        return ResponseResult.success("删除成功");
    }

    private MemberBankCardRequest toMemberBankCardRequest(BankCardRequest request) {
        MemberBankCardRequest memberBankCardRequest = new MemberBankCardRequest();
        memberBankCardRequest.setId(request.getId());
        memberBankCardRequest.setMemberId(getLoginMember().getMemberId());
        memberBankCardRequest.setCardNo(request.getCardNo());
        memberBankCardRequest.setCardIdNo(getLoginMember().getAuthIdNo());
        memberBankCardRequest.setCardName(getLoginMember().getAuthName());
        memberBankCardRequest.setMobile(getLoginMember().getAuthMobile());
        memberBankCardRequest.setBankCode(request.getBankCode());
        return memberBankCardRequest;
    }

}
