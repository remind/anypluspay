package com.anypluspay.channel;

import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author wxj
 * 2025/2/22
 */
public class B {

    public String b() {
        System.out.println("b:" + Thread.currentThread().getId());
        return "b";
    }

    public Mono<String> c() {
        System.out.println("c" + Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        throw new RuntimeException("123");
        return Mono.just("c");
//        return Mono.just("c").delayElement(Duration.ofSeconds(2));
    }
}
