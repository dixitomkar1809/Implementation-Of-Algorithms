package ond170030.LP1;

import java.util.*;

public class ShuntingYard {

	private static int getPrecedence(String s) {
		switch(s) {
			
		case "+":{
			return 1;
		}
		
		case "-":{
			return 1;
		}
		
		case "*":{
			return 2;
		}
		
		case "/":{
			return 2;
		}
		
		case "%":{
			return 2;
		}
		
		case "^":{
			return 3;
		}
		}
		return 0;
	}
	
//	private static boolean getDominator(String s1, String s2) {
//		return ((getPrecedence(s2) > 0) && getPrecedence(s2)  >= getPrecedence(s1));
//	}
	
	private static boolean getRightPrecedence(String s1) {
		if(s1=="^") {
			return true;
		}
		return false;
	}
	
	public static String shuntingYard(String[] expr) {
		StringBuilder output = new StringBuilder();
		Deque<String> stack = new LinkedList<>();
		for(int i = 0; i < expr.length; i++) {
			String token = expr[i];
			if(getPrecedence(token) > 0) {
				while(!stack.isEmpty() && (getPrecedence(stack.peek()) > getPrecedence(token) || (getPrecedence(token) == getPrecedence(stack.peek()) && !getRightPrecedence(token)) && !stack.peek().equals("("))) {
					output.append(stack.pop());
				}
				stack.push(token);
			}else if(token == "(") {
				stack.push(token);
			}else if(token == ")") {
				while(!stack.peek().equals("(")) {
					output.append(stack.pop());
				}
				stack.pop();
			}else {
				output.append(token);
			}		
		}
		while(!stack.isEmpty()) {
			output.append(stack.pop());
		}
		return output.toString();
		
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] arr = {"a", "+", "b", "+", "c"};
		System.out.println(shuntingYard(arr));
		String[] arr2 = {"a", "*", "b", "+", "c"};
		System.out.println(shuntingYard(arr2));
		String[] arr3 = {"a", "+", "b", "*", "c"};
		System.out.println(shuntingYard(arr3));
		String[] arr4 = {"a", "^", "b", "^", "c"};
		System.out.println(shuntingYard(arr4));
		String[] arr5 = {"(", "(", "A", "+", "B", ")","-","C", "*", "(", "D", "/", "E", ")", ")", "+", "F"};
		System.out.println(shuntingYard(arr5));
		
	}

}
