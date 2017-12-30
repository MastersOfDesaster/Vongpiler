package vong.piler.her.lexer;

import org.apache.commons.lang.StringEscapeUtils;

public enum TokenTypeEnum {
    START("(was ist das fuer 1 code?).*"), // start of program
    END("(1 nicer!!!).*"), // end of program
    
    // function
    CMD("(was ist das fuer 1).*"), // call function
    PSTART("(vong).*"), // start of parameters
    PNEXT("(,).*"), // next parameter
    PEND("(her?).*"), // end of parameter
    
    // variable
    VSTART("(i bims 1).*"), // declare variable
    ASSI("(goenn dir).*"), // assign value
    VEND("(!!!).*"), // end of variable declaration

    // whitespace
    WHITESPACE("( ).*"), NEWLINE("(\n).*"),

    // types
    TYPE("(zal|word|isso).*"),

    // constants
    CONST_ZAL("\\b(\\d{1,9})\\b.*"), // constant of type zal (number)
    CONST_ISSO("\\b(yup|nope)\\b.*"), // constant of type isso (boolean)
    CONST_WORD("\\\"(.*?)\\\".*"), // constant of type word (string)

    NAME("\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*"); // name/identifier

    private String regEx;

    TokenTypeEnum(String regEx) {
        this.regEx = regEx;
    }

    public String getRegEx() {
        return this.regEx;
    }

    public static String toMarkdown() {
        String ebnf = "";
        ebnf += "# Lexer Grammar\n\n";
        ebnf += "|Token|Regular Expression|\n";
        ebnf += "|-----|------------------|\n";
        for (TokenTypeEnum tokenType : TokenTypeEnum.values()) {
            String regex = StringEscapeUtils.escapeJava(tokenType.getRegEx());
            regex = regex.replaceAll("\\|", "\\\\|");
            ebnf += "|" + tokenType.name() + "|" + regex + "|\n";
        }
        return ebnf;
    }
}
