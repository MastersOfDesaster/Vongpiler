package vong.piler.her.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;
import vong.piler.her.lexer.Lexer;
import vong.piler.her.lexer.Token;
import vong.piler.her.lexer.TokenTypeEnum;
import vong.piler.her.logger.LoggerVongManagerHer;

public class Parser { 
	
	private static Logger logger = LoggerVongManagerHer.getLogger(Parser.class);
	
	private TreeNode parent;
	private TreeNode root;
	
	Map<TokenTypeEnum, List<TokenTypeEnum>> ruleMap = new EnumMap<TokenTypeEnum, List<TokenTypeEnum>>(TokenTypeEnum.class);
	
    public Parser() {
    	// Add rules to map
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
    
    public TreeNode parse(List<Token> tokenList) {
    	
    	List<TokenTypeEnum> rule = new ArrayList<TokenTypeEnum>();    	

    	if(tokenList.get(tokenList.size()-1).getType().equals(TokenTypeEnum.END)) {
	    	for (Token t : tokenList) {
	    		
	    		// Token != START
	    		if (!(t.getType().equals(TokenTypeEnum.START)) && !(rule.isEmpty())) {    			
	    			// Syntax ok
	    			if(rule.contains(t.getType())){    				
	     			}
	    			//Syntax fail
	    			else {
	    				String error = new String();
	    				for(TokenTypeEnum tte : rule) {
	    					error = error + tte.getRegEx() +"|";
	    				}
	    				if(t.getContent().isEmpty()) {
	    					logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getLabel() + " -->  Expected: " + error.substring(0, (error.length()-1)));
	    				}
	    				else {
	    					logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getContent() + " -->  Expected: " + error.substring(0, (error.length()-1)));
	    				}
	    				System.exit(0);
	    			}
	    		}
	    		// Token != START and first token
	    		else if(!(t.getType().equals(TokenTypeEnum.START)) && rule.isEmpty()){
	    			if(t.getContent().isEmpty()) {
	    				logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getLabel() + " --> Expected: was ist das fuer 1 code?");
	    			}
	    			else {
	    				logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getContent() + " --> Expected: was ist das fuer 1 code?");
	    			}
					System.exit(0);    		    			
	    		}
	    		else {
	
	    		}
	    		
	    		parseItem(t);
	    		
				rule = ruleMap.get(t.getType());    		
	    	}
    	}
    	else {
			if(tokenList.get(tokenList.size()-1).getContent().isEmpty()) {
				logger.error("syntax error in line " + tokenList.get(tokenList.size()-1).getLine() + ": Got: " + tokenList.get(tokenList.size()-1).getLabel() + " --> Expected: 1 nicer!!!");
			}
			else {
				logger.error("syntax error in line " + tokenList.get(tokenList.size()-1).getLine() + ": Got: " + tokenList.get(tokenList.size()-1).getContent() + " --> Expected: 1 nicer!!!");
			}
			System.exit(0);  
    	}
    	
    	return root;
    	   	
    }
    
    private void parseItem(Token t) {
    	
    	// Root node
    	if (root == null) {
    		logger.debug("Token: " + t.getType());
			root = new TreeNode(t.getType(), null);
			parent = root;
    	}
    	// Node without value
    	else if(t.getContent().isEmpty()) {
    		logger.debug("Token: " + t.getType());
 	        parent.setRight(new TreeNode(t.getType(),parent));
	        parent = parent.getRight();

	    // Node with value
		}else {
			logger.debug("Value: " + t.getContent() + " Token: " + t.getType());			
			parent.setRight(new TreeNode(t.getType(), parent));
			parent = parent.getRight();
           	parent.setLeft(t.getContent());
		}       
    }
}