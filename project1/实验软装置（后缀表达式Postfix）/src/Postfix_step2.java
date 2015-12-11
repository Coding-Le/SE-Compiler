import java.io.*;
class Parser {
	static int lookahead;   //向前看标识符
	public Parser() throws IOException {
		lookahead = System.in.read();     // read from the stream previously
	}
	void expr() throws IOException {
		term();
		rest();
	}
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
				System.out.write('+');
			} else if (lookahead == '-') {
				match('-');
				term();
				System.out.write('-');
			} else {
				flag = false;
			}
		}
	}
	void term() throws IOException {
		if (Character.isDigit((char)lookahead)) {
			System.out.write((char)lookahead);
			match(lookahead);
		} else throw new Error("syntax error");
	}
	void match(int t) throws IOException {
		if (lookahead == t) lookahead = System.in.read();
		else throw new Error("syntax error");
	}
}
public class Postfix {
	public static void main(String[] args) throws IOException {
		System.out.println("Input an infix expression and output its postfix natation: ");
		new Parser().expr();
		System.out.println("\nEnd of program.");
	}
}
