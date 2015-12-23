/**
 * A simple java program which receives a Infix expression and change it into a Postfix expression
 * The input can be 0~9, +, -, version 1.0 can only process with such character, any mistake input can cause an exception
 * Version 2.0 provides error-detection institution, the extra blank space, missing number between two operator, missing 
 *  operator between two number, missing left or right operator, invalid input character will be recognized by the program,
 *  and hint will be provided for user to correct the mistakes at one time
 *  
 * @author <strong>Yu Le</strong>
 * @version 2.0
 * @since JDK1.8
 */
import java.io.*;
import java.util.*;
import java.io.IOException;
import  java.lang.SecurityManager;
class Parser {
	static int lookahead;
	Integer position;
	ArrayList<String>error_list = new ArrayList<String>();
	String output_Postfix;
	boolean state;
	String input_Infix;
	/**
	 * Creates a new <code>Parser</code> object based on the
	 * input stream.Initialize some variables for the following
	 * program to use, read one character from the input stream
	 * to start the program
	 * @throws IOException
     * @exception IOException 
	 */
	public Parser() throws IOException {
		lookahead = System.in.read();
		position = 0;
		state = true;
		output_Postfix = "";
		input_Infix = "";
		input_Infix += (char)lookahead;
	}
	/**
	 * Extract an expression from input stream, until meeting a '\n' or Enter, processing the expression is done by 
	 * term() and rest(), and a state will be given indicating the correctness of the expression, if the expression is 
	 * valid, corresponding postfix will be printed, otherwise the error details will be given.
	 * @see #term()
	 * @see #rest()
	 * @throws IOException
     * @exception IOException 
	 */
	void expr() throws IOException {
		term();
		rest();
		if (state) {
			System.out.print("The input is valid, and the corresponding output is "+output_Postfix+'\n');
		} else {
			System.out.print("The input is invalid, and there are totally "+new Integer(error_list.size()).toString()+" error \n");
			for (int i = 0; i < error_list.size(); i++) {
				System.out.print(new Integer(i+1).toString()+": "+error_list.get(i)+'\n');
			}
		}
	}
	/**
	 * In version 1.0, this function only processes the '+','-', and call term() to process the digit, 
	 * once meeting the CRLF, the program will end normally
	 * In version 2.0, we introduce the error detection, if there are ' ', digit, invalid character, the
	 * program will give corresponding error message, and the variable state will change into false
	 * @see #match(int)
	 * @see #term()
	 * @throws IOException
     * @exception IOException 
	 */
	void rest() throws IOException {
		/*if (lookahead == '+') {
			match('+');
			term();
			System.out.write('+');
			rest();
		} else if (lookahead == '-') {
			match('-');
			term();
			System.out.write('-');
			rest();
		} else {
			//do nothing
		}*/
		boolean flag = true;
		while (flag) {
			if (lookahead == '+') {
				match('+');
				term();
				//System.out.write('+');
				output_Postfix += '+';
			} else if (lookahead == '-') {
				match('-');
				term();
				//System.out.write('-');
				output_Postfix += '-';
			} else if ((char)lookahead == '\n' || lookahead == 13) {
				flag = false;
			} else if (Character.isDigit((char)lookahead)) {
				String temp = "Syntax error, there should be +/- between position" + position.toString()+ "(" + input_Infix.charAt(position-1) + ") and position" + new Integer(position+1).toString()+"(" + (char)lookahead +")";
				error_list.add(temp);
				state = false;
				term();
			} else if ((char)lookahead == ' ') {
				Integer space_num = 0;
				while ((char)lookahead == ' ') {
					match(lookahead);
					space_num++;
				}
				state = false;
				position++;
				String temp = "Syntax error, there are "+ space_num.toString()+" extra blank space between position"+new Integer(position-space_num).toString() +" and position" + position.toString();
				position--;
				error_list.add(temp);
				rest();
			} else {
				String temp = "Lexical error, the character(" + (char)lookahead + ") at position"+position.toString()+" is invalid input, replace it with +/-";
				error_list.add(temp);
				match(lookahead);
				state = false;
				term();
			}
		}
	}
	/**
	 * In version 1.0, this function only process with digit, and other character will be processed by rest()
	 * In version 2.0, we introduce the error detection, if there are ' ', '+', '-', CRLF, invalid character, 
	 * the program will give corresponding error message, and the variable state will change into false
	 * @see #match(int)
	 * @see #rest()
	 * @throws IOException
     * @exception IOException 
	 */
	void term() throws IOException {
		if (Character.isDigit((char)lookahead)) {
			//System.out.write((char)lookahead);
			Character temp_char = (char)lookahead;
			output_Postfix += temp_char.toString();
			match(lookahead);
		} else {
			if ((char)lookahead == ' ') {
				Integer space_num = 0;
				while ((char)lookahead == ' ') {
					match(lookahead);
					space_num++;
				}
				state = false;
				position++;
				String temp = "Syntax error, there are "+ space_num.toString()+" extra blank space between position"+new Integer(position-space_num-1).toString() +" and position" + position.toString();
				position--;
				error_list.add(temp);
				term();
			} else if ((char)lookahead == '+' || (char)lookahead == '-') {
				if (position == 0) {
					String temp = "Syntax error, there is missing a left operator at position" + new Integer(position+1).toString();
					error_list.add(temp);
				} else {
					String temp = "Syntax error, there is missing a number between position"+ position.toString() + "(" + input_Infix.charAt(position-1) + ") and position"+new Integer(position+1).toString()+"(" + (char)lookahead + ")";
					error_list.add(temp);
				}
				state = false;
			} else if ((char)lookahead == '\n' || lookahead == 13) {
				position++;
				String temp = "Syntax error, there is missing a right operator at position" + position.toString();
				error_list.add(temp);
				state = false;
			} else {
				String temp = "Lexical error, the character(" + (char)lookahead + ") at position"+position.toString()+" is invalid input, replace it with 0~9";
				error_list.add(temp);
				match(lookahead);
				state = false;
			}
			//throw new Error("syntax error");
		}
	}
	/**
	 * Compare the character t and lookahead, if they are equal, 
	 * we read the next character from stream, otherwise we give out exception.
	 * @param t is the character provided by <code>term</code> or <code>match</code>, the current operating parameter
	 * @throws IOException
     * @exception IOException 
	 */
	void match(int t) throws IOException {
		if (lookahead == t) {
			lookahead = System.in.read();
			input_Infix += (char)lookahead;
			position++;
		} else {
			throw new Error("Syntax error");
		}
	}
}
public class Postfix {
	/**
	 * the main function of this class, it gives out the input message and end message,
	 * creating a Parser to handle one input stream line, and end the program after processing it.
	 * @see Class #Parser
	 * @see Class #Parser()
	 * @see Class #expr()
	 * @throws IOException
	 * @exception IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Input an infix expression and output its postfix natation: ");
		new Parser().expr();
		System.out.println("\nEnd of program.");
	}
}

