package vong.piler.her.parser;

import java.util.ArrayList;
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

    	
    	for (Token t : tokenList) {
    		
    		// Toke != START
    		if (!(t.getType().equals(TokenTypeEnum.START))) {    			
    			// Syntax ok
    			if(rule.contains(t.getType())){    				
    				parseItem(t);		
    				
    			}
    			//Syntax fail
    			else {    				
    				System.out.println("Syntax error:" + t);
    				System.exit(0);
    			}
    		// Token == START and first token
    		}else if(t.getType().equals(TokenTypeEnum.START) && rule.isEmpty()){
    			System.out.println("Token:" + t.getType());
    			parseItem(t);
    			
    		}else {
				System.out.println("Syntax error: START missing" + t);
				System.exit(0);
    		}
    		
			rule = ruleMap.get(t.getType());    		
    	}
    	
    	return root;
    	   	
    }
    
    private void parseItem(Token t) {
    	
    	// Root node
    	if (root == null) {
          root = new TreeNode(t.getType());
          parent = root;
    	}
    	// Node without value
    	else if(t.getContent().isEmpty()) {
			System.out.println("Token: " + t.getType());
	        parent.setRight(new TreeNode(t.getType()));
	        parent = parent.getRight();

	    // Node with value
		}else {
			System.out.println("Value: " + t.getContent() + " Token: " + t.getType());
			parent.setRight(new TreeNode(t.getType()));
			parent = parent.getRight();
           	parent.setLeft(t.getContent());
		}       
    }
}