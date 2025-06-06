package com.anypluspay.channel;

/**
 * @author wxj
 * 2024/11/28
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        A a = new A();

        A b = a;
        a = null;
        System.out.println(a);
        System.out.println(b);
    }



}
