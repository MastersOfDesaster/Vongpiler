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
    
    ruleMap.put(TokenTypeEnum.START,Arrays.asList(new TokenTypeEnum [] {TokenTypeEnum.START, TokenTypeEnum.VAR}));
    }
    
    public void parse(List<Token> tokenList) {
    	
		System.out.println("Parser:");
    	
    	for (Token t : tokenList) {

    		System.out.println(t);
    	}
    	
    	
    	
    	
    }
    
    private void parseItem() {
        
    }
}
