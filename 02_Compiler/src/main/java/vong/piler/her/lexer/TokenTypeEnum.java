package vong.piler.her.lexer;

public enum TokenTypeEnum {
    START("\\b(was ist das für 1 code?)\\b.*"), // start of program
    END("1 nicer!"), // end of program
    
    // variable
    VTYPE("\\b(i bims 1)\\b.*"), // set type of var
    VNAME("\\b(i bims 1)\\b.*"), // set name of var
    VASSI("\\b(gönn dir)\\b.*"); // assign value
    
    // function call

    private String regEx;
    
    TokenTypeEnum(String regEx) {
        this.regEx = regEx;
    }
    
    public String getRegEx() {
        return this.regEx;
    }
}
