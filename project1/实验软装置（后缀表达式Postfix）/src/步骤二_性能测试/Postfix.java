import java.io.*;

class Parser {
	static int lookahead;
	static String test_string;
	static int indexs;
	public Parser() throws IOException {
		test_string = "";
		test_string = new Runner().get_string();
		indexs = 0;
		lookahead = test_string.charAt(indexs);
	}

	void expr() throws IOException {
		long startTime=System.currentTimeMillis();   //获取开始时间
		term();
		rest();
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("\n程序运行时间： "+(endTime-startTime)+"ms\n");
	}

	void rest() throws IOException {
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
		} else  throw new Error("syntax error");
	}

	void match(int t) throws IOException {
		if (lookahead == t)  {
			lookahead = test_string.charAt(++indexs);
		}
		else  throw new Error("syntax error");
	}
}

public class Postfix {
	public static void main(String[] args) throws IOException {
		System.out.println("Input an infix expression and output its postfix notation:");
		new Parser().expr();
		System.out.println("\nEnd of program.");
	}
}
