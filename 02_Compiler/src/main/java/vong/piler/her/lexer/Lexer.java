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
    int line;
    private static Logger logger = LogManager.getLogger(Constants.loggerName);

    public Lexer() {
        // Print Token-Grammar
        // System.out.println(TokenTypeEnum.toMarkdown());
    }

    public List<Token> lex(String source) throws Exception {
        this.source = source;
        this.line = 1;

        List<Token> tokenList = new ArrayList<Token>();

        if (this.source.isEmpty()) {
            return tokenList;
        }

        do {
            Token token = nextToken();
            if (token != null) {
                switch (token.getType()) {
                case WHITESPACE:
                    break;
                case NEWLINE:
                    // increase line number
                    line++;
                    break;
                default:
                    tokenList.add(token);
                    logger.debug(token);
                    System.out.println(token);
                }
            } else {
                logger.error("invalid token!\n" + this.source);
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
                case TYPE:
                case NAME:
                case CONST_ZAL:
                case CONST_WORD:
                case CONST_ISSO:
                    token = new Token(line, tokenType, matcher.group(1));
                    break;
                default:
                    token = new Token(line, tokenType);
                }

                // calculate lexed-length and cut from source-string
                int matchlength = matcher.group(1).length();
                switch (tokenType) {
                case WHITESPACE:
                case NEWLINE:
                case END:
                    break;
                case CONST_WORD:
                    // we have to skip the quotation mark at end
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
