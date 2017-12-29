package vong.piler.her.lexer;

public class Token {
    private TokenTypeEnum type;
    private String value;

    public Token(TokenTypeEnum type) {
        this.type = type;
    }

    public Token(TokenTypeEnum type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenTypeEnum getType() {
        return type;
    }

    public void setType(TokenTypeEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        if (this.value != null) {
            return "(" + this.type.name() + "," + this.value + ")";
        } else {
            return "(" + this.type.name() + ")";
        }
    }
}
