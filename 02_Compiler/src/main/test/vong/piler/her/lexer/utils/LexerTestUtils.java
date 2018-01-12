package vong.piler.her.lexer.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import vong.piler.her.lexer.Token;
import vong.piler.her.lexer.TokenTypeEnum;

public class LexerTestUtils {
    public static String file2String(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/test/vong/piler/her/lexer/resources", fileName)));
    }
    
    public static List<TokenTypeEnum> tokenList2TypeList(List<Token> tokenList) {
        List<TokenTypeEnum> typeList = new ArrayList<TokenTypeEnum>(); 
        for (Token token: tokenList) {
            typeList.add(token.getType());
        }
        return typeList;
    }
    
    public static boolean compareLists(List<TokenTypeEnum> expectedList, List<TokenTypeEnum> parsedList) {
        boolean equal = false;
        
        if (expectedList.size() == parsedList.size()) {
            parsedList.removeAll(expectedList);
            if (parsedList.isEmpty()) {
                equal = true;
            }
        }
        
        return equal;
    }
}
