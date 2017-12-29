package vong.piler.her.lexer;

public class Token {
    private TokenTypeEnum type;
    private String value;
    private int line;

    public Token(int line, TokenTypeEnum type) {
        this.line = line;
        this.type = type;
    }

    public Token(int line, TokenTypeEnum type, String value) {
        this.line = line;
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
            return this.line + ": (" + this.type.name() + "," + this.value + ")";
        } else {
            return this.line + ": (" + this.type.name() + ")";
        }
    }
}
