package ond170030.LP1;

import java.util.Stack;

public class postfixEval {
	
	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public static int postfixtEval(String[] exp) {
		
		Stack<Integer> stack = new Stack<>();
		
		for(int i = 0; i < exp.length; i++) {
			String c = exp[i];
			
			if(isNumeric(c)) {
//				stack.push(c - "0");
			}else {
				int temp1 = stack.pop();
				int temp2 = stack.pop();
				
				switch(c) {
					
				case "+":{
					stack.push(temp2 + temp1);
					break;
				}
				case "-":{
					stack.push(temp2 - temp1);
					break;
				}
				case "*":{
					stack.push(temp2 * temp1);
					break;
				}
				case "/":{
					stack.push(temp2 / temp1);
					break;
				}
				}
			}
			
			
		}
		
		return stack.pop();
	}
	
	public static void main(String args[]) {
		String[] exp= {"2","3","1","*","+","9","-"};
        System.out.println(postfixtEval(exp));
	}

}
