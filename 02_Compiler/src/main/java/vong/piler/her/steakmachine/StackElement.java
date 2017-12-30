package vong.piler.her.steakmachine;




public class StackElement {
	public enum Type{
		ADDRESS, ZAL, ISSO, WORD
	}
	
	public StackElement(Type type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	private Type type;
	private Object value;
	
	public String toString() {
		switch(type) {
		case ADDRESS:
			return "adress: " + value.toString();
		case ISSO:
			return "isso: " + (((boolean) value)? "yup" : "nope");
		case ZAL:
			return "zal: " + value.toString();
		case WORD:
			return "word: " + value.toString();
		default:
			return "unknown type on stack";		
		}
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	

}
