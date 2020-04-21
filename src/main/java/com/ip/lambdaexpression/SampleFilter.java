package com.ip.lambdaexpression;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PRATAP.
 * Wap to use filter method.
 */
public final class SampleFilter {

/**
 * This is private constructor.
 */
private SampleFilter() {
}

/**
 * @param num number.
 * @return list.
 */
public static List<Integer> findOutEvenNum(final List<Integer> num) {
List<Integer> list = num.stream()
.filter(i -> i % 2 == 0)
.collect(Collectors.toList());
return list;
}

}

