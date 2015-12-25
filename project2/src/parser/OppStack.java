
package parser;
import java.util.Stack;
import exceptions.*;

public class OppStack{
	private Stack<Token> terminalStack;
	private Stack<Token> nonterminalStack;
	
	public OppStack(){
		terminalStack = new Stack<Token>();
		nonterminalStack = new Stack<Token>();
		terminalStack.push(new Token(Type.END,"$"));
	}
	
	//check the top Token of the terminalStack
	public Token topMostTerminal(){
		return terminalStack.peek();
	}
	
	public void pushTerminal(Token t){
		terminalStack.push(t);
	}
	
	public void pushNonterminal(Token t){
		nonterminalStack.push(t);
	}
	
	public Token popTerminal() throws MissingOperatorException{
		if(terminalStack.isEmpty()) 
			throw new MissingOperatorException();
		return terminalStack.pop();
	}
	
	public Token popNonterminal() throws MissingOperandException{
		if(nonterminalStack.isEmpty()) 
			throw new MissingOperandException();
		Token result = nonterminalStack.pop();
		return result;
	}
	
	
}