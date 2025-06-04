package com.anypluspay.admin.demo.cashier;

import com.anypluspay.admin.demo.cashier.request.PayMethod;
import com.anypluspay.payment.types.asset.AssetType;

import java.util.List;

/**
 * 收银台配置，暂时写死，后续可做成配置
 *
 * @author wxj
 * 2025/5/20
 */
public class CashierConfig {

    /**
     * 支付时，支持的支付方式
     */
    public static final List<PayMethod> PAY_METHODS_OF_PAY = List.of(getBalancePayMethod(), getBankCardPayMethod(), getAliPayMethod());

    /**
     * 充值时，支持的支付方式
     */
    public static final List<PayMethod> PAY_METHODS_OF_DEPOSIT = List.of(getBankCardPayMethod(), getAliPayMethod());

    /**
     * 获取所有支付时的支付方式
     *
     * @return
     */
    public static List<PayMethod> getPayMethodsOfPay() {
        return PAY_METHODS_OF_PAY;
    }

    /**
     * 获取支付时指定的支付方式
     *
     * @param payMethodCode
     * @return
     */
    public static PayMethod getPayMethodOfPay(String payMethodCode) {
        return PAY_METHODS_OF_PAY.stream().filter(payMethod -> payMethod.getCode().equals(payMethodCode)).findFirst().get();
    }

    /**
     * 获取充值时支持的支付方式
     *
     * @return
     */
    public static List<PayMethod> getPayMethodsOfDeposit() {
        return PAY_METHODS_OF_DEPOSIT;
    }

    /**
     * 获取充值时指定的支付方式
     * @param payMethodCode
     *
     * @return
     */
    public static PayMethod getPayMethodOfDeposit(String payMethodCode) {
        return PAY_METHODS_OF_DEPOSIT.stream().filter(payMethod -> payMethod.getCode().equals(payMethodCode)).findFirst().get();
    }

    private static PayMethod getBalancePayMethod() {
        PayMethod payMethod = new PayMethod();
        payMethod.setCode("balance");
        payMethod.setName("余额支付");
        payMethod.setPayModel("BALANCE");
        payMethod.setAssetType(AssetType.BALANCE.getCode());
        payMethod.setPayInst("101"); // 使用常规余额
        return payMethod;
    }

    private static PayMethod getBankCardPayMethod() {
        PayMethod payMethod = new PayMethod();
        payMethod.setCode("testBank");
        payMethod.setName("银行卡支付");
        payMethod.setPayModel("ONLINE_BANK");
        payMethod.setAssetType(AssetType.BANKCARD.getCode());
        payMethod.setPayInst("ALL_BANKCARD"); // 不限制银行卡
        return payMethod;
    }

    private static PayMethod getAliPayMethod() {
        PayMethod payMethod = new PayMethod();
        payMethod.setCode("alipay");
        payMethod.setName("支付宝支付");
        payMethod.setPayModel("ONLINE_BANK");
        payMethod.setAssetType(AssetType.ALIPAY.getCode());
        payMethod.setPayInst("ALIPAY_WEB"); // 不限制银行卡
        return payMethod;
    }
}
