package vong.piler.her.vongruntime.virtualmachine.model;




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
			return "address: " + value.toString();
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
	
	public String print() {
		switch(type) {
		case ADDRESS:
			return value.toString();
		case ISSO:
			return (((boolean) value)? "yup" : "nope");
		case ZAL:
			return value.toString();
		case WORD:
			return value.toString();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StackElement other = (StackElement) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	

}
