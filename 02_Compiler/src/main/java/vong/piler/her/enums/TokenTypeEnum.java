package vong.piler.her.enums;

import org.apache.commons.lang.StringEscapeUtils;

public enum TokenTypeEnum {
    // program
    START("(was ist das fuer 1 code\\?).*", "was ist das fuer 1 code?"), // start
    END("(1 nicer!!!).*", "1 nicer!!!"), // end
    
    // function
    CMD("(was ist das fuer 1).*", "was ist das fuer 1"), // call function
    PSTART("(vong).*", "vong"), // start of parameters
    PNEXT("((,|\\?|\\+){1}).*", ",|?|+"), // next parameter
    PEND("(her\\?).*", "her?"), // end of parameter
    
    // print
    PRINT("(gieb).*", "gieb"),
    AAL("(halo i bims!!!).*", "halo i bims!!!"),
    
    // if
    IFSTART("(bist du).*", "bist du"),
    IFEND("(real rap).*", "real rap"),
    
    // jump
    HASHTAG("#\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*", "#"),
    GOTOSTART("(g zu).*", "g zu"),
    GOTOEND("(du larry!!!).*", "du larry!!!"),
    
    // declare variable
    VSTART("(i bims 1).*", "i bims 1"), // declare variable
    ASSI("(goenn dir).*", "goenn dir"), // assign value
    VEND("(!!!).*", "!!!"), // end of variable declaration

    // whitespace
    COMMENT(":X(.*?)\n.*"), WHITESPACE("( |\t).*"), NEWLINE("(\n).*"),

    // types
    TYPE("(zal\\h|word\\h|isso\\h).*", "zal|word|isso"),

    // constants
    CONST_ZAL("\\b(\\d{1,9})\\b.*", "const_zal"), // constant of type zal (number)
    CONST_ISSO("(yup|nope).*", "const_isso"), // constant of type isso (boolean)
    CONST_WORD("\\\"(.*?)\\\".*", "const_word"), // constant of type word (string)

    // input
    INPUT("(1gabe).*", "1gabe"), // screen input

    // name / identifier
    NAME("\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*", "name"),
	FNAME("", "fname");
    
    private String regEx;
    private String label;

    TokenTypeEnum(String regEx) {
        this.regEx = regEx;
    }
    
    TokenTypeEnum(String regEx, String label) {
        this.regEx = regEx;
        this.label = label;
    }

    public String getRegEx() {
        return this.regEx;
    }
    
    public String getLabel() {
        return this.label;
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
