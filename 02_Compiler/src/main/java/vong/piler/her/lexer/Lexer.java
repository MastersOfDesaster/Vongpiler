package vong.piler.her.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    int index;

    public Lexer() {
    }

    public List<Token> lex(String source) throws Exception {
        List<Token> tokenList = new ArrayList<Token>();

        if (source.isEmpty()) {
            return tokenList;
        }

        index = 0;
        do {
            Token token = nextToken(source);
            if (token != null) {
                tokenList.add(token);
                System.out.println(token);
            } else {
                System.out.println("invalid token!");
                break;
            }
        } while (index < source.length());

        return tokenList;
    }

    Token nextToken(String source) {
        Token token = null;

        for (TokenTypeEnum tokenType : TokenTypeEnum.values()) {
            Pattern pattern = Pattern.compile(".{" + index + "}" + tokenType.getRegEx(), Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source);
            
            if (matcher.matches()) {
                token = new Token(tokenType);
                index += matcher.group(1).length() + 1;
                break;
            }
        }

        return token;
    }
}
