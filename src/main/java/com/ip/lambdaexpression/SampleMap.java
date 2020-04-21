package com.ip.lambdaexpression;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PRATAP.
 * Wap to use map method.
 */
public final class SampleMap {
/**
 *This is private constructor.
 */
private SampleMap() {

}

/**
 * @param num number.
 * @return list.
 */
public static List<Integer> multiplyNum(final List<Integer> num) {
List<Integer> list = num.stream().map(i -> i * i).collect(Collectors.toList());
return list;

}
}

