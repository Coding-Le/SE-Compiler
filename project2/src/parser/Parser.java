
package parser;

import java.util.LinkedList;
import java.lang.Math;
import exceptions.*;

public class Parser{
	
	private OppStack oppStack_p;
	private Lexer scanner;
	private Semantic analyzer;
	
	public Parser(String expression)
	{
		oppStack_p = new OppStack();
		scanner = new Lexer(expression);
		analyzer = new Semantic();
	}
	
	double ParsingResult() throws ExpressionException{
		
		Token lookahead = scanner.getNextToken();
		Token Token_bef = new Token(Type.END,"$");
		
		if(lookahead.getType()==Type.END)
			throw new EmptyExpressionException();
		
		while(true){
			Token topOp = oppStack_p.topMostTerminal();
			
			//Use to distinguish the operator Negative or Minus
			if(lookahead.getType()==Type.MINUS &&!(Token_bef.getType()==Type.DECIMAL
					||Token_bef.getType()==Type.RIGHTPAREN))
				lookahead.setType(Type.NEGATIVE);
			
			
			int priority = OppTable.Table[topOp.getType()][lookahead.getType()];
			
			//The condition of accepting
			if(topOp.getType() == Type.END && lookahead.getType()== Type.END)
				return accept();
			
			//The condition of shift
			if(priority == 1 || priority == 2){
				oppStack_p.pushTerminal(lookahead);

				//****************************************
				System.out.println("shift  " + lookahead.getValue());

				Token_bef = lookahead;
				lookahead = scanner.getNextToken();
			}
			else if(priority == 3){//The condition of reduce
				int pr_t;
				LinkedList<Token> reduceTer = new LinkedList<Token>();
				do {
					topOp = oppStack_p.popTerminal();
					reduceTer.addFirst(topOp);
					pr_t = OppTable.Table[oppStack_p.topMostTerminal().getType()][topOp.getType()];
				} while (pr_t == 2 || pr_t == 3 );
				reduce(reduceTer);
			}
			else if(priority >=10 ){
				switch(priority){
				case 10:
					throw new SyntacticException();
				case 11:
					throw new MissingOperatorException();
				case 12:
					 throw new MissingOperandException();
				case 13:
					 throw new MissingLeftParenthesisException();
				case 14:
					 throw new MissingRightParenthesisException();
				case 15:
					 throw new FunctionCallException();
				case 16:
					 throw new TrinaryOperationException();
				case 17:
					 throw new TypeMismatchedException();
				}
			}
			
		}
	}
	
	double accept()throws MissingOperandException,
		TypeMismatchedException{
		
		Token result = oppStack_p.popNonterminal();
		analyzer.checkDecimal(result);

		//*********************************************************
		System.out.println("accept "+(Double)result.getValue());

		return (Double)result.getValue();
	}
	
	void reduce(LinkedList<Token> Ters) throws 
	 MissingOperandException, TypeMismatchedException,
	 DividedByZeroException, MissingRightParenthesisException,
	 TrinaryOperationException,FunctionCallException{
		
		Token temp1,temp2;
		Double a,b;
		Boolean c,d;
			
		//****************************************
		System.out.println("reduce  " + Ters.get(0).getValue());

		switch(Ters.get(0).getType())
		{

		case Type.PLUS:
			if(Ters.size() == 1){
				
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.DECIMAL,a+b));}
				break;
		case Type.MINUS:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.DECIMAL,a-b));}
				break;
		case Type.MULTIPLY:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.DECIMAL,a*b));}
				break;
		case Type.DIVIDE:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				if(b==0) 
					throw new DividedByZeroException();
				oppStack_p.pushNonterminal(new Token(Type.DECIMAL,a/b));}
				break;
		case Type.POWER:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.DECIMAL,Math.pow(a, b)));}
				break;
		case Type.NEGATIVE:
			if(Ters.size() == 1){
				
				Token t = oppStack_p.popNonterminal();
				analyzer.checkDecimal(t);
				t.setValue((Double)t.getValue()*(-1));
				oppStack_p.pushNonterminal(t);}
				break;
				
		case Type.AND:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkLogic(temp1, temp2);
				d=(Boolean)temp1.getValue();
				c=(Boolean)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,c&&d));}
				break;
		case Type.OR:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkLogic(temp1, temp2);
				d=(Boolean)temp1.getValue();
				c=(Boolean)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,c||d));}
				break;
		case Type.NOT:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				analyzer.checkLogic(temp1);
				c=(Boolean) temp1.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,!c));}
				break;
				
		case Type.EQUAL:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,a.equals(b)));}
				break;
		case Type.NOTEQUAL:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,!a.equals(b)));}
				break;
		case Type.GREATER:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,a>b));}
				break;
		case Type.GREATEREQUAL:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,a>=b));}
				break;
		case Type.LESS:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,a<b));}
				break;
		case Type.LESSEQUAL:
			if(Ters.size() == 1){
				temp1 = oppStack_p.popNonterminal();
				temp2 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(temp1, temp2);
				b=(Double)temp1.getValue();
				a=(Double)temp2.getValue();
				oppStack_p.pushNonterminal(new Token(Type.LOGIC,a<=b));}
				break;
				
		case Type.DECIMAL:
			if(Ters.size() == 1){
				oppStack_p.pushNonterminal(Ters.get(0));}
				break;
				
		case Type.LOGIC:
			if(Ters.size() == 1){
				oppStack_p.pushNonterminal(Ters.get(0));}
				break;
			
		case Type.LEFTPAREN:
			if(Ters.size() == 2
					&&Ters.get(1).getType() == Type.RIGHTPAREN)
				;
			else
				throw new  MissingRightParenthesisException();
				break;
		case Type.QUERY:
			if(Ters.size() == 2
				&& Ters.get(1).getType() == Type.COLON){
				Token t = oppStack_p.popNonterminal();
				Token t1 = oppStack_p.popNonterminal();
				analyzer.checkDecimal(t,t1);
				Token t_l =  oppStack_p.popNonterminal();
				analyzer.checkLogic(t_l);
				if((Boolean)t_l.getValue())
					oppStack_p.pushNonterminal(t1);
				else
					oppStack_p.pushNonterminal(t);
				}
				else
					throw new TrinaryOperationException();
				break;
			
		case Type.SIN:
				
			if(Ters.get(1).getType()==Type.LEFTPAREN
					&&Ters.get(2).getType()==Type.RIGHTPAREN
						&& Ters.size()==3){
				Token t = oppStack_p.popNonterminal();
				analyzer.checkDecimal(t);
				double parameter = (Double)t.getValue();
					
				oppStack_p.pushNonterminal(new Token(Type.DECIMAL,(double)Math.sin(parameter)));
				}
			else
				throw new FunctionCallException();
			break;
		case Type.COS:
			if(Ters.get(1).getType()==Type.LEFTPAREN
					&&Ters.get(2).getType()==Type.RIGHTPAREN
					&& Ters.size()==3){
				Token t = oppStack_p.popNonterminal();
				analyzer.checkDecimal(t);
				double parameter = (Double)t.getValue();
				oppStack_p.pushNonterminal(new Token(Type.DECIMAL,(double)Math.cos(parameter)));
			}
			else
				throw new FunctionCallException();
			break;	
			
		case Type.MAX:
		{
			Token t = oppStack_p.popNonterminal();
			analyzer.checkDecimal(t);
			double max_num = (Double)t.getValue();
			if(Ters.size()<4)
				throw new MissingOperandException();
			else if(Ters.get(1).getType()!=Type.LEFTPAREN || Ters.getLast().getType() != Type.RIGHTPAREN)
				throw new FunctionCallException();
			else{
				for(int i=2; i<Ters.size()-1; i++)
					if(Ters.get(i).getType()!=Type.COMMA)
						throw new FunctionCallException();
					else{
						Token t2 = oppStack_p.popNonterminal();
						analyzer.checkDecimal(t2);
						double max_t = (Double)t2.getValue();
						if(max_t > max_num)
							max_num = max_t;
						}		
				}
			oppStack_p.pushNonterminal(new Token(Type.DECIMAL,max_num));
			break;
		}
		case Type.MIN:
		{
			Token t = oppStack_p.popNonterminal();
			analyzer.checkDecimal(t);
			double min_num = (Double)t.getValue();
			if(Ters.size()<4)
				throw new MissingOperandException();
			else if(Ters.get(1).getType()!=Type.LEFTPAREN || Ters.getLast().getType() != Type.RIGHTPAREN)
				throw new FunctionCallException();
			else{
				for(int i=2; i<Ters.size()-1; i++)
					if(Ters.get(i).getType()!=Type.COMMA)
						throw new FunctionCallException();
					else{
						Token t2 = oppStack_p.popNonterminal();
						analyzer.checkDecimal(t2);
						double min_t = (Double)t2.getValue();
						if(min_t < min_num)
							min_num = min_t;
						}		
				}
			oppStack_p.pushNonterminal(new Token(Type.DECIMAL,min_num));
			break;
			}
		}	
	}	
}