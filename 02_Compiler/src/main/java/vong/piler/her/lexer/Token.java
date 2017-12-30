package vong.piler.her.lexer;

public class Token {
    private TokenTypeEnum type;
    private String content = "";
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
    
    public int getLine() {
        return this.line;
    }

    public String toString() {
        String text = this.line + ": " + this.type.name();
        
        if (!this.content.isEmpty()) {
            text += ": " + this.content + "";
        } else {
            text += "(" + this.type.getLabel() + ")";
        }
        
        return text;
    }
}
