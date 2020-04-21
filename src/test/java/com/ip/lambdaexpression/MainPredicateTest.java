package com.ip.lambdaexpression;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MainPredicateTest {
MainPredicate predicate;

@BeforeTest
public void setUp() {
	predicate = new MainPredicate();
}

@Test
public void checkStringLength() {
	boolean actual = predicate.checkLengthOfString("hello");
	boolean expected = true;
	Assert.assertEquals(actual, expected);
}
}
