package vong.piler.her.lexer;

public enum TokenTypeEnum {
    START("(was ist das für 1 code?).*"), // start of program
    END("(1 nicer!!!).*"), // end of program
    
    // constants
    CONST_ZAL("\\b(\\d{1,9})\\b.*"),
    CONST_ISSO("\\b(yup|nope)\\b.*"),
    CONST_WORD("\\\"(.*?)\\\".*"),
    
    // function
    FUNCTION("(was ist das fuer 1).*"),
    FPAR_START("(vong).*"),
    FPAR_END("(her?).*"),
    SUME("(sume).*"),
    ONE("(one).*"),
    MAHL("(mal).*"),
    TEILUNG("(teilung).*"),
    RÄST("(räst).*"),
    
    // assignments
    ASSI("(gönn dir).*"), // assign value
    
    // whitespace
    WHITESPACE("( |!!!).*"),
    NEWLINE("(\n).*"),
    
    // variable
    VAR("(i bims 1).*"),
    VTYPE("(zal|word|isso).*"), // type is zal
    VNAME("\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*"); // set name of var

    private String regEx;
    
    TokenTypeEnum(String regEx) {
        this.regEx = regEx;
    }
    
    public String getRegEx() {
        return this.regEx;
    }
}
