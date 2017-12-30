package vong.piler.her.parser;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;
import vong.piler.her.lexer.Token;
import vong.piler.her.lexer.TokenTypeEnum;

public class Parser { 
	
	private static Logger logger = LogManager.getLogger(Constants.loggerName);
	
	Map<TokenTypeEnum, List<TokenTypeEnum>> ruleMap = new EnumMap<TokenTypeEnum, List<TokenTypeEnum>>(TokenTypeEnum.class);
	
	   
	
    public Parser() {    
    ruleMap.put(TokenTypeEnum.START,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.VSTART}));
    ruleMap.put(TokenTypeEnum.VSTART,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.TYPE}));
    ruleMap.put(TokenTypeEnum.TYPE,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.NAME}));
    ruleMap.put(TokenTypeEnum.NAME,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.ASSI, TokenTypeEnum.PEND, TokenTypeEnum.PNEXT, TokenTypeEnum.PSTART}));
    ruleMap.put(TokenTypeEnum.ASSI,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.CONST_ISSO, TokenTypeEnum.CONST_WORD, TokenTypeEnum.CONST_ZAL}));
    ruleMap.put(TokenTypeEnum.CONST_ISSO,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.PEND, TokenTypeEnum.PNEXT, TokenTypeEnum.VEND}));
    ruleMap.put(TokenTypeEnum.CONST_WORD,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.PEND, TokenTypeEnum.PNEXT, TokenTypeEnum.VEND}));
    ruleMap.put(TokenTypeEnum.CONST_ZAL,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.PEND, TokenTypeEnum.PNEXT, TokenTypeEnum.VEND}));
    ruleMap.put(TokenTypeEnum.VEND,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.VSTART, TokenTypeEnum.CMD}));
    ruleMap.put(TokenTypeEnum.CMD,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.NAME}));
    ruleMap.put(TokenTypeEnum.PSTART,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.CONST_ISSO, TokenTypeEnum.CONST_WORD, TokenTypeEnum.CONST_ZAL, TokenTypeEnum.PEND, TokenTypeEnum.NAME}));
    ruleMap.put(TokenTypeEnum.PNEXT,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.CONST_ISSO, TokenTypeEnum.CONST_WORD, TokenTypeEnum.CONST_ZAL}));
    ruleMap.put(TokenTypeEnum.PEND,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.CMD, TokenTypeEnum.END}));
    ruleMap.put(TokenTypeEnum.END,Arrays.asList(new TokenTypeEnum [] {}));
    }
    
    public void parse(List<Token> tokenList) {
    	

    	
    	for (Token t : tokenList) {
    		
    		if (!(t.equals(TokenTypeEnum.START))) {
    		
    			
    		}
    		
   
    		
    		
    		
    		
    	}
    	
    	
    	
    	
    }
    
    private void parseItem() {
        
    }
}
