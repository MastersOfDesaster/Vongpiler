package vong.piler.her.lexer;

public enum TokenTypeEnum {
    START("(was ist das für 1 code?).*"), // start of program
    END("(1 nicer!!!).*"), // end of program
    
    // variable
    VAR("(i bims 1).*"),
    VTYPE("(zal|word|isso).*"), // type is zal
    VNAME("\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*"), // set name of var
    VASSI("(gönn dir).*"), // assign value
    
    // constants
    CZAL("\\b(\\d{1,9})\\b.*"),
    CWORD("\\\"(.*?)\\\".*"),
    CISSO("\\b(yup|nope)\\b.*"),
    
    // whitespace
    WHITESPACE("( |\n|!!!).*");
    
    // function call

    private String regEx;
    
    TokenTypeEnum(String regEx) {
        this.regEx = regEx;
    }
    
    public String getRegEx() {
        return this.regEx;
    }
}
