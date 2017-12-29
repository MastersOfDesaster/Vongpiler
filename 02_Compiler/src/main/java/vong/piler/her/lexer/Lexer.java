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
        System.out.println(source);
        List<Token> tokenList = new ArrayList<Token>();

        if (source.isEmpty()) {
            return tokenList;
        }

        index = 0;
        do {
            Token token = nextToken(source);
            if (token != null) {
                if (token.getType() != TokenTypeEnum.WHITESPACE) {
                    tokenList.add(token);
                    System.out.println(token);
                }
            } else {
                System.out.println("invalid token!");
                break;
            }
        } while (index < source.length());

        return tokenList;
    }

    private Token nextToken(String source) {
        Token token = null;

        for (TokenTypeEnum tokenType : TokenTypeEnum.values()) {
            Pattern pattern = Pattern.compile(".{" + index + "}" + tokenType.getRegEx(), Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source);

            if (matcher.matches()) {
                switch (tokenType) {
                case CZAL:
                case CWORD:
                case CISSO:
                    token = new Token(tokenType, matcher.group(1));
                    break;
                default:
                    token = new Token(tokenType);
                }

                index += matcher.group(1).length() + 1;
                break;
            }
        }

        return token;
    }
}
