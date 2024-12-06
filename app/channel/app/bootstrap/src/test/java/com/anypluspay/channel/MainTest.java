package com.anypluspay.channel;

import com.anypluspay.commons.lang.types.NumberRang;

/**
 * @author wxj
 * 2024/11/28
 */
public class MainTest {

    public static void main(String[] args) {

//        Range<Integer> range1 = Range.open(1, 5);
//        System.out.println(range1.contains(1));
//        System.out.println(range1.contains(3));
//        System.out.println(range1.contains(5));
//        System.out.println(range1.contains(6));
//        System.out.println(Range.downTo(3, BoundType.OPEN));


        NumberRang numberRang = NumberRang.parse("[1.0,1.5)");
        System.out.println(numberRang.contains(1));
        System.out.println(numberRang.contains(3));
        System.out.println(numberRang.contains(5));
        System.out.println(numberRang.contains(6));
        System.out.println(numberRang);

        System.out.println("Hello world!");
    }
}
