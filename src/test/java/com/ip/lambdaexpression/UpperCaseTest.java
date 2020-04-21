package com.ip.lambdaexpression;

import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UpperCaseTest {

/**
 *Declaring uppercase class as a datamember in it.
 */
private UpperCase upperCase = null;

/**
 *Create the object of implement class.
 */
@BeforeTest
public void setup() {
upperCase = new UpperCase();
}

/**
 *First Test case to check with lower case letter.
 */
@Test
public void multipleWordsToUppercase() {
    List<String> input = Arrays.asList("a", "b", "hello");
    List<String> actual = upperCase.allToUpperCase(input);
    List<String> expected = Arrays.asList("A", "B", "HELLO");
    Assert.assertEquals(expected, actual);
    //System.out.println(actual);
}
}
