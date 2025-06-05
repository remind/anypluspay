package com.anypluspay.channel.application.context;

/**
 * @author wxj
 * 2025/6/5
 */
public class ChannelContext {

    private static final ThreadLocal<Object> context = new ThreadLocal<>();

    public static void set(Object obj) {
        context.set(obj);
    }

    public static Object get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
