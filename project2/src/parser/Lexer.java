
package parser;

import exceptions.*;

class Lexer{
	private String str;
	
	//The position that scanner points.
	private int ptr;

	public Lexer(String s){
		str = s + "$";
		ptr = 0;
	}
	
	public Token getNextToken()throws IllegalIdentifierException, 
	IllegalSymbolException, IllegalDecimalException {
		
		//Ignore the Space
		char t = str.charAt(ptr);
		while(t == ' '){
			t = str.charAt(++ptr);
		}
		
		if(ptr+1 == str.length() && str.charAt(ptr) == '$'){
			
			return new Token(Type.END);
		}
		else if(Character.isDigit(str.charAt(ptr)))
			return DecimalParse();
		else if(Character.isLetter(str.charAt(ptr)))
			return WordParse();
		else
			return operatorParse();
	}
	
	private Token DecimalParse()throws IllegalDecimalException{
		double result,integral,fraction,exponent,divider;
		boolean isPositive = true;
		int state = 0;
		
		result = integral = fraction = exponent =0;
		divider = 1;
		
		while(true){
			
			switch(state){
			case 0: 
				if(Character.isDigit(str.charAt(ptr))){
					integral += Character.getNumericValue(str.charAt(ptr++));
					state = 1;
				}
				else
					throw new IllegalDecimalException();
				break;
			case 1:
				if(Character.isDigit(str.charAt(ptr))){
					integral = integral*10 + Character.getNumericValue(str.charAt(ptr++));
					state = 1;
				}else if (str.charAt(ptr) == '.') {
					ptr++;
					state = 2;
				} else if (str.charAt(ptr) == 'e'||str.charAt(ptr) == 'E') {
					ptr++;
					state = 4;
				} else
					state = 7;
				break;
			case 2:
				if (Character.isDigit(str.charAt(ptr))) {
					double fra_t = Character.getNumericValue(str.charAt(ptr++));
					divider = divider / 10.0;
					fraction = fraction + fra_t * divider;
					state = 3;
				} else
					throw new IllegalDecimalException();
				break;
			case 3:
				if (Character.isDigit(str.charAt(ptr))) {
					double fra_t = Character.getNumericValue(str.charAt(ptr++));
					divider = divider / 10.0;
					fraction = fraction + fra_t * divider;
					state = 3;
				}else if (str.charAt(ptr) == 'e'||str.charAt(ptr) == 'E') {
					ptr++;
					state = 4;
				} else
					state = 7;
				break;
			case 4:
				if(Character.isDigit(str.charAt(ptr))){
					exponent = exponent*10 + Character.getNumericValue(str.charAt(ptr++));
					state = 6;
					}
				else if (str.charAt(ptr) == '+') {
					isPositive = true;
					ptr++;
					state = 5;
				} else if (str.charAt(ptr) == '-') {
					isPositive = false;
					ptr++;
					state = 5;
				} else
					throw new IllegalDecimalException();
				break;
			case 5:
				if(Character.isDigit(str.charAt(ptr))){
					exponent = exponent*10 + Character.getNumericValue(str.charAt(ptr++));
					state = 6;
					}
				else
					throw new IllegalDecimalException();
				break;
			case 6:
				if(Character.isDigit(str.charAt(ptr))){
					exponent = exponent*10 + Character.getNumericValue(str.charAt(ptr++));
					state = 6;
					}
				else
					state = 7;
				break;
			case 7:
				if (!isPositive)
					exponent = -exponent;
				result = (integral + fraction) * Math.pow(10.0, exponent);
				return new Token(Type.DECIMAL,new Double(result));
			}
		}
	}
	
	private Token WordParse()throws IllegalIdentifierException{
		
		int begin = ptr;
		String result = "";
		
		while(Character.isLetter(str.charAt(ptr))){
			ptr++;
		}
		
		result = str.substring(begin, ptr).toLowerCase();
		
		if(Type.getType(result)== -1 
				&& !result.equals("true")
					&& !result.equals("false"))
			throw new IllegalIdentifierException();
		else{
			
			if(result.equals("true"))
				return new Token(Type.LOGIC,Boolean.parseBoolean("true"));
			else if(result.equals("false"))
				return new Token(Type.LOGIC,Boolean.parseBoolean("false"));
			else
				return new Token(Type.getType(result),result);
		}
	}
	
	private Token operatorParse()throws IllegalIdentifierException,
		IllegalDecimalException,IllegalSymbolException{
		
		String result = "" + str.charAt(ptr);
		String temp = "" + str.charAt(ptr) + str.charAt(ptr+1);
		
		if(Type.getType(temp) != -1){
			ptr+=2;
			return new Token(Type.getType(temp),temp);
		}
		
		if(Type.getType(result)== -1)
			throw new IllegalIdentifierException();
		else if(Type.getType(result)!= -1){
			ptr++;
			return new Token(Type.getType(result),result);
		}
		else if(str.charAt(ptr) == '.')
			throw new IllegalDecimalException();
		else
			throw new IllegalSymbolException();
			
	}
}	
