package com.ip.lambdaexpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SampleMapTest {

/**
 *
 */
private SampleMap samplemap = null;

/**
*
*/
@BeforeTest
public void setUP() {
//samplemap = new SampleMap();
}

/**
* 
*/
@Test
public void multiplyNumTest() {
List<Integer> list = new ArrayList<Integer>();
list.add(5);
list.add(10);
list.add(20);
list.add(10);
List<Integer> actual= SampleMap.multiplyNum(list);
List<Integer> expected = Arrays.asList(25, 100, 400, 100);
Assert.assertEquals(actual, expected);
}
}
