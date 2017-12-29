package vong.piler.her.parser;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;
import vong.piler.her.lexer.Token;

public class Parser { 
	
	private static Logger logger = LogManager.getLogger(Constants.loggerName);
	
    public Parser() {
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
