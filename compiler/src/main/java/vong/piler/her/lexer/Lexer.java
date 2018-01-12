package vong.piler.her.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import vong.piler.her.enums.TokenTypeEnum;

import org.apache.logging.log4j.LogManager;

import com.vdurmont.emoji.EmojiParser;

public class Lexer {
    private String source;
    private int line;
    private static Logger logger = LogManager.getLogger(Lexer.class);

    public Lexer() {
        // Print Token-Grammar
        // System.out.println(TokenTypeEnum.toMarkdown());
    }

    public List<Token> lex(String source) throws Exception {
        // replace unicode emojis by text-based emojis
        this.source = EmojiParser.parseToAliases(source);
        
        // we count lines from 1 to n
        this.line = 1;

        List<Token> tokenList = new ArrayList<Token>();

        // return empty tokenlist, when source is empty
        if (this.source.isEmpty()) {
            return tokenList;
        }

        do {
            Token token = nextToken();
            if (token != null) {
                switch (token.getType()) {
                case COMMENT:
                case WHITESPACE:
                    break;
                case NEWLINE:
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
                    token.setContent(matcher.group(1).trim());
                    break;
                case NAME:
                case CONST_ZAL:
                case CONST_WORD:
                case CONST_ISSO:
                case HASHTAG:
                    token.setContent(matcher.group(1));
                    break;
                default:
                }

                String content = matcher.group(1);
                int matchlength = content.length();
                switch (tokenType) {
                case CONST_WORD:
                    matchlength += 2; // skip the quotation marks at start and
                                      // end
                    break;
                case HASHTAG:
                    matchlength++; // skip "#" at beginning
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
