package vong.piler.her.lexer;

import org.apache.commons.lang.StringEscapeUtils;

public enum TokenTypeEnum {
    START("(was ist das fuer 1 code?).*"), // start of program
    END("(1 nicer!!!).*"), // end of program

    // constants
    CONST_ZAL("\\b(\\d{1,9})\\b.*"), CONST_ISSO("\\b(yup|nope)\\b.*"), CONST_WORD("\\\"(.*?)\\\".*"),

    // function
    CMD("(was ist das fuer 1).*"), PSTART("(vong).*"), PEND("(her?).*"),

    // assignments
    ASSI("(goenn dir).*"), // assign value

    // whitespace
    WHITESPACE("( |!!!).*"), NEWLINE("(\n).*"),

    // variable
    VAR("(i bims 1).*"), TYPE("(zal|word|isso).*"), // type is zal
    NAME("\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*"); // set name of var

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
