package vong.piler.her.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;

public class Lexer {
    String source;

    public Lexer() {
    }

    public List<Token> lex(String source) throws Exception {
        this.source = source;

        List<Token> tokenList = new ArrayList<Token>();

        if (this.source.isEmpty()) {
            return tokenList;
        }

        do {
            Token token = nextToken();
            if (token != null) {
                if (token.getType() != TokenTypeEnum.WHITESPACE) {
                    tokenList.add(token);
                    System.out.println(token);
                }
            } else {
                System.out.println("invalid token!\n" + this.source);
                // System.out.println(source.substring(index));
                break;
            }
        } while (!this.source.isEmpty());

        return tokenList;
    }

    private Token nextToken() {
        Token token = null;

        for (TokenTypeEnum tokenType : TokenTypeEnum.values()) {
            Pattern pattern = Pattern.compile(tokenType.getRegEx(), Pattern.DOTALL);

            Matcher matcher = pattern.matcher(source);

            if (matcher.matches()) {
                // create token with or without value
                switch (tokenType) {
                case VTYPE:
                case VNAME:
                case CZAL:
                case CWORD:
                case CISSO:
                    token = new Token(tokenType, matcher.group(1));
                    break;
                default:
                    token = new Token(tokenType);
                }

                // calculate lexed-length and cut from source-string
                int matchlength = matcher.group(1).length();
                switch (tokenType) {
                case WHITESPACE:
                case END:
                    break;
                case CWORD:
                    matchlength += 2;
                    break;
                default:
                    matchlength++;
                }
                this.source = this.source.substring(matchlength);
                break;
            }
        }

        return token;
    }
}
