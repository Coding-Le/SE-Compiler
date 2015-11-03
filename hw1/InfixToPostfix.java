import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfix {
	    private static String convertExpr(String expression){
		   Stack<Character>oper = new Stack<Character>();  //use stack to store the operator
		   int length = expression.length();
		   char my_arr[] = expression.toCharArray();         //the infix string in array format
		   char target_arr[] = new char[length];             //to store the postfix string in array format
		   int target_pos = 0;
		   int position = 0;
		   for (position = 0; position < length; position++) {
			   if (my_arr[position] == '+' || my_arr[position] == '-' || my_arr[position] == '*' || my_arr[position] == '/' || my_arr[position] == '^') {
					   while (!oper.isEmpty() && priority(oper.peek()) >= priority(my_arr[position])) {
						   target_arr[target_pos++] = oper.pop();  //if the priority of stack's top operator is higher, pop it to the postfix
					   }
					   oper.push(my_arr[position]);   //current operator must be pushed into stack after removing some operator from stack 
			   } else if (my_arr[position] == '(') {     
				   oper.push(my_arr[position]);      //no matter which operator in the stack, '(' should be pushed into stack directly
			   } else if (my_arr[position] == ')') {
				   while (oper.peek() != '(') {
					   target_arr[target_pos++] = oper.pop();
				   }
				   oper.pop();                     //the '(' should be thrown away
			   } else {
				   target_arr[target_pos++] = my_arr[position];  //except the operator, number or character should added to the postfix directly
			   }
		   }
		   while (!oper.isEmpty()) {
			   target_arr[target_pos++] = oper.pop();     //pop the remaining operator in the stack to the postfix string
		   }
		   String to_return = new String(target_arr);    //change the array format into string format
		   return to_return;
		}
	    private static int priority(char c) {  //define the calculation priority, the rank is: ^ * / + -
	    	switch(c) {
	    	case '+':
	    	case '-': return 1;
	    	case '*':
	    	case '/': return 2;
	    	case '^': return 3;
	    	}
	    	return -1;
	    }
		public static void main(String[] args){
		 while (true) {
		   //System.out.print("Please input the infix string your want to convert:\n");
		   Scanner scan = new Scanner(System.in);
		   String infix_expression = scan.next();
		   //System.out.print("The corresponding postfix string is:\n");
		   String postfix_expression = convertExpr(infix_expression);
		   System.out.print(postfix_expression);
		   System.out.print('\n');
			}
		}
}
