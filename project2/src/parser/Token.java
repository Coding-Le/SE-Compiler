
package parser;

class Token {

	// The token type such as decimal,plus.
	private int type;

	//The token value. 
	private Object value;

	//Constructed function with the given type and value.
	Token(int type, Object value) {
		this.type = type;
		this.value = value;
	}

	// Constructed function with the given token. 
	Token(Token origin) {
		this.type = origin.type;
		this.value = origin.value;
	}

	// Constructed function with the given type.
	Token(int type) {
		this.type = type;
		this.value = null;
	}

	//Function to get or set type or value.
	public int getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
	
	public void setType(int t) {
		type = t;
	}

	public void setValue(Object t) {
		value = t;
	}
}