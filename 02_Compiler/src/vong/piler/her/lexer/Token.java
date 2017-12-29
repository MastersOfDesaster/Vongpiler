package vong.piler.her.lexer;

public class Token {
    private TypeEnum type;
    private String value;
    
    public TypeEnum getType() {
        return type;
    }
    public void setType(TypeEnum type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
