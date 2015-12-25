
package parser;

public class Type {	
	
	static String Type_string[] = {"$", "DECIMAL", "+", "-","*", "/", "^",
			"-", "LOGIC", "|", "&", "!", "<", "<=" , ">", ">=",
			"=", "<>", "?", ":", "(", "," , ")" , "max", "min",
			"sin" , "cos"};
	
	//$,the end flag
	 public final static int END = 0;
	//DECIMAL(value)
	 public final static int DECIMAL = 1;

	//binary operators from DECIMAL to DECIMAL
	 public final static int PLUS = 2;
	 public final static int MINUS = 3;
	 public final static int MULTIPLY = 4;	 
	 public final static int DIVIDE = 5;
	 public final static int POWER = 6;

	//unary operators on DECIMAL
	 public final static int NEGATIVE = 7; 	 

	//LOGIC(True or False)
	 public final static int LOGIC = 8;

	//operators on LOGIC
	 public final static int OR	= 9; 
	 public final static int AND = 10;	 
	 public final static int NOT = 11;

	//binary operators from DECIMAL to LOGIC
	 public final static int LESS	= 12; 
	 public final static int LESSEQUAL = 13;	
	 public final static int GREATER = 14;	 
	 public final static int GREATEREQUAL = 15;
	 public final static int EQUAL	= 16; 
	 public final static int NOTEQUAL	= 17; 
	 
	//trinary operator ? :
	 public final static int QUERY = 18;
	 public final static int COLON = 19;

	//punctuation
	 public final static int LEFTPAREN = 20;
	 public final static int COMMA = 21;	 
	 public final static int RIGHTPAREN = 22;
	 
	//predefined functions
	 public final static int MAX = 23;
	 public final static int MIN = 24;	 
	 public final static int SIN = 25;	 
	 public final static int COS = 26;
	 
	 public static int getType(String typeName){
		 for(int i =0; i<27; i++)
			 if(Type_string[i].equals(typeName))
				 return i;
		 return -1;
	 }
	 
}