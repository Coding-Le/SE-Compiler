import java.io.IOException;

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

