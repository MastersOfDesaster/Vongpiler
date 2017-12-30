package vong.piler.her.lexer;

public class Token {
    private TokenTypeEnum type;
    private String content = "";
    private String label = "";
    private int line;

    public Token(int line, TokenTypeEnum type) {
        this.line = line;
        this.type = type;
    }

    public TokenTypeEnum getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public int getLine() {
        return this.line;
    }

    public String toString() {
        if (!this.content.isEmpty()) {
            return this.line + ": " + this.type.name() + ": " + this.content + "";
        } else {
            return this.line + ": " + this.type.name() + "(" + this.label + ")";
        }
    }
}
