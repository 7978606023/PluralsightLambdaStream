package com.ip.lambdaexpression;

import java.util.function.BinaryOperator;

public class BinaryOperators {
	
	public Integer additionTwoNum(int num1, int num2) {
		
		BinaryOperator<Integer> func2 = (x1, x2) -> x1 + x2;
		
	      return func2.apply(num1, num2);

	}
}
