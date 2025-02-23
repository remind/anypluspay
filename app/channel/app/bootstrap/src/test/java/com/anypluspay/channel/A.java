package com.anypluspay.channel;

/**
 * @author wxj
 * 2025/2/22
 */
public class A {

    public void test() {
        B b = new B();
        b.c().subscribe(r -> {
            System.out.println("r" + Thread.currentThread().getId());
        });
        b.b();
    }
}
