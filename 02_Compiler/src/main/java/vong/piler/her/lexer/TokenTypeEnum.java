package vong.piler.her.lexer;

public enum TokenTypeEnum {
    START("\\b(was ist das für 1 code?)\\b.*"), // start of program
    END("\\b(1 nicer!)\\b.*"), // end of program
    
    // variable
    VZAL("\\b(i bims 1 zal)\\b.*"), // type is zal
    VWORD("\\b(i bims 1 word)\\b.*"), // type is word
    VISSO("\\b(i bims 1 isso)\\b.*"), // type is isso
    VNAME("\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*"), // set name of var
    VASSI("\\b(gönn dir)\\b.*"), // assign value
    
    // constants
    CZAL("\\b(\\d{1,9})\\b.*"),
    CWORD("\'(.*?)\'"),
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
