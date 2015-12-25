
package parser;
import exceptions.*;

public class Semantic{	
	public void checkDecimal(Token input)throws TypeMismatchedException{
		if(input.getType()!= Type.DECIMAL) 
			throw new TypeMismatchedException();
	}
	
	public void checkDecimal(Token input,Token input2)throws TypeMismatchedException{
		if(input.getType()!= Type.DECIMAL || input2.getType() != Type.DECIMAL) 
			throw new TypeMismatchedException();
	}
	
	public void checkLogic(Token input)throws TypeMismatchedException{
		if(input.getType() != Type.LOGIC)
			throw new TypeMismatchedException();
	}
	
	public void checkLogic(Token input, Token input2)throws TypeMismatchedException{
		if(input.getType() != Type.LOGIC || input2.getType() != Type.LOGIC)
			throw new TypeMismatchedException();
	}
}