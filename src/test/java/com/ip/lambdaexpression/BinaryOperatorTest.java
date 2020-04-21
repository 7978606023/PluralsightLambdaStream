package com.ip.lambdaexpression;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BinaryOperatorTest {

	 BinaryOperators binaryoperators;
	 
	 @BeforeTest
	 public void setUp() {
		 binaryoperators = new BinaryOperators();
	 }
	 @Test
	 public void testTwoNum() {
		Integer actual = binaryoperators.additionTwoNum(2, 3);
		Integer expected = 5;
		Assert.assertEquals(actual, expected);
	 }
	 
	 @Test
	 public void testTwoNegNum() {
		Integer actual = binaryoperators.additionTwoNum(-2, -3);
		Integer expected = -5;
		Assert.assertEquals(actual, expected);
	 }
	 
	 @Test
	 public void testOneNegNum() {
		Integer actual = binaryoperators.additionTwoNum(-2, 3);
		Integer expected = 1;
		Assert.assertEquals(actual, expected);
	 }
}
