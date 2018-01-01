package vong.piler.her.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Lexer {
    private String source;
    private int line;
    private static Logger logger = LogManager.getLogger(Lexer.class);

    public Lexer() {
        // Print Token-Grammar
        //System.out.println(TokenTypeEnum.toMarkdown());
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
                case COMMENT:
                    // increase line number
                    line++;
                    break;
                default:
                    tokenList.add(token);
                    logger.debug(token);
                }
            } else {
                logger.error("tokeng unbekamd:" + this.source);
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
                // create token
                token = new Token(line, tokenType);

                // set value or label
                switch (tokenType) {
                case TYPE:
                case NAME:
                case CONST_ZAL:
                case CONST_WORD:
                case CONST_ISSO:
                case HASHTAG:
                    token.setContent(matcher.group(1));
                    break;
                default:
                }

                // calculate lexed-length and cut from source-string
                int matchlength = matcher.group(1).length();
                switch (tokenType) {
                case COMMENT:
                    matchlength += 3; // skip ":X" and "\n"
                    break;
                case CONST_WORD:
                    matchlength += 2; // skip the quotation marks at start and
                                      // end
                    break;
                case HASHTAG:
                    matchlength++; // skip hashtag
                    break;
                default:
                    break;
                }
                this.source = this.source.substring(matchlength);
                break;
            }
        }

        return token;
    }
}
