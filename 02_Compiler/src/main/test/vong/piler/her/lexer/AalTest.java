package vong.piler.her.lexer;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import vong.piler.her.lexer.utils.LexerTestUtils;

public class AalTest {
    Lexer lexer;
    List<TokenTypeEnum> tokenList;
    
    @Before
    public void initLexer() {
        this.lexer = new Lexer();
        this.tokenList = new ArrayList<TokenTypeEnum>();
    }
    
    @Test
    public void aalTest() {
        this.tokenList.add(TokenTypeEnum.START);
        this.tokenList.add(TokenTypeEnum.AAL);
        this.tokenList.add(TokenTypeEnum.END);
        
        List<TokenTypeEnum> typeList;
        try {
            String source = LexerTestUtils.file2String("AalTest.vsh");
            typeList = LexerTestUtils.tokenList2TypeList(this.lexer.lex(source));
            if (!LexerTestUtils.compareLists(this.tokenList, typeList)) {
                fail();
            }
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
