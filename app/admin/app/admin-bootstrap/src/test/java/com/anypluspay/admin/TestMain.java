package com.anypluspay.admin;

/**
 * @author wxj
 * 2024/11/5
 */
public class TestMain {
    public static void main(String[] args) {
        TestEnum testEnum =  TestEnum.valueOf("TEST");
        System.out.println(testEnum);
        String code = testEnum.toString();
        System.out.println(code);
        System.out.println(testEnum.getDisplayName());

    }
}
