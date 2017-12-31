package vong.piler.her.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import vong.piler.her.lexer.Token;
import vong.piler.her.lexer.TokenTypeEnum;
import vong.piler.her.logger.LoggerVongManagerHer;

public class Parser {

	private static Logger logger = LoggerVongManagerHer.getLogger(Parser.class);

	private TreeNode parent;
	private TreeNode root;

	Map<TokenTypeEnum, List<TokenTypeEnum>> ruleMap = new EnumMap<TokenTypeEnum, List<TokenTypeEnum>>(
			TokenTypeEnum.class);

	public Parser() {
		// Add rules to map
		ruleMap.put(TokenTypeEnum.START, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.VSTART,
				TokenTypeEnum.HASHTAG, TokenTypeEnum.GOTOSTART, TokenTypeEnum.AAL }));
		ruleMap.put(TokenTypeEnum.VSTART, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.TYPE }));
		ruleMap.put(TokenTypeEnum.TYPE, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.NAME }));
		ruleMap.put(TokenTypeEnum.NAME, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.ASSI, TokenTypeEnum.PEND,
				TokenTypeEnum.PNEXT, TokenTypeEnum.PSTART, TokenTypeEnum.VEND }));
		ruleMap.put(TokenTypeEnum.ASSI, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CONST_ISSO,
				TokenTypeEnum.CONST_WORD, TokenTypeEnum.CONST_ZAL, TokenTypeEnum.CMD, TokenTypeEnum.NAME}));
		ruleMap.put(TokenTypeEnum.CONST_ISSO, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.PEND,
				TokenTypeEnum.PNEXT, TokenTypeEnum.VEND, TokenTypeEnum.PRINT }));
		ruleMap.put(TokenTypeEnum.CONST_WORD, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.PEND,
				TokenTypeEnum.PNEXT, TokenTypeEnum.VEND, TokenTypeEnum.PRINT }));
		ruleMap.put(TokenTypeEnum.CONST_ZAL, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.PEND,
				TokenTypeEnum.PNEXT, TokenTypeEnum.VEND, TokenTypeEnum.PRINT }));
		ruleMap.put(TokenTypeEnum.VEND,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.VSTART, TokenTypeEnum.CMD, TokenTypeEnum.PRINT,
						TokenTypeEnum.AAL, TokenTypeEnum.IFSTART, TokenTypeEnum.HASHTAG, TokenTypeEnum.GOTOSTART,
						TokenTypeEnum.NAME, TokenTypeEnum.IFEND, TokenTypeEnum.END }));
		ruleMap.put(TokenTypeEnum.CMD, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.NAME }));
		ruleMap.put(TokenTypeEnum.PSTART, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CONST_ISSO,
				TokenTypeEnum.CONST_WORD, TokenTypeEnum.CONST_ZAL, TokenTypeEnum.PEND, TokenTypeEnum.NAME }));
		ruleMap.put(TokenTypeEnum.PNEXT, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CONST_ISSO,
				TokenTypeEnum.CONST_WORD, TokenTypeEnum.CONST_ZAL, TokenTypeEnum.NAME }));
		ruleMap.put(TokenTypeEnum.PEND,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CMD, TokenTypeEnum.PRINT, TokenTypeEnum.AAL,
						TokenTypeEnum.IFSTART, TokenTypeEnum.IFEND, TokenTypeEnum.HASHTAG, TokenTypeEnum.GOTOSTART,
						TokenTypeEnum.NAME, TokenTypeEnum.END }));
		ruleMap.put(TokenTypeEnum.PRINT, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CONST_ISSO,
				TokenTypeEnum.CONST_WORD, TokenTypeEnum.CONST_ZAL, TokenTypeEnum.NAME }));
		ruleMap.put(TokenTypeEnum.AAL,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.VSTART, TokenTypeEnum.CMD, TokenTypeEnum.PRINT,
						TokenTypeEnum.AAL, TokenTypeEnum.IFSTART, TokenTypeEnum.IFEND, TokenTypeEnum.HASHTAG,
						TokenTypeEnum.GOTOSTART, TokenTypeEnum.END }));
		ruleMap.put(TokenTypeEnum.IFSTART, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.PSTART }));
		ruleMap.put(TokenTypeEnum.IFEND,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.IFEND, TokenTypeEnum.CMD, TokenTypeEnum.PRINT,
						TokenTypeEnum.AAL, TokenTypeEnum.IFSTART, TokenTypeEnum.HASHTAG, TokenTypeEnum.GOTOSTART,
						TokenTypeEnum.END }));
		ruleMap.put(TokenTypeEnum.HASHTAG,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.VSTART, TokenTypeEnum.CMD, TokenTypeEnum.PRINT,
						TokenTypeEnum.IFSTART, TokenTypeEnum.GOTOSTART, TokenTypeEnum.GOTOEND, TokenTypeEnum.AAL }));
		ruleMap.put(TokenTypeEnum.GOTOSTART, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.HASHTAG }));
		ruleMap.put(TokenTypeEnum.GOTOEND,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CMD, TokenTypeEnum.PRINT, TokenTypeEnum.AAL,
						TokenTypeEnum.IFSTART, TokenTypeEnum.HASHTAG, TokenTypeEnum.GOTOSTART, TokenTypeEnum.END }));
		ruleMap.put(TokenTypeEnum.END, Arrays.asList(new TokenTypeEnum[] {}));
	}

	public TreeNode parse(List<Token> tokenList) {

		List<TokenTypeEnum> rule = new ArrayList<TokenTypeEnum>();

		// String type = null;

		if (tokenList.get(tokenList.size() - 1).getType().equals(TokenTypeEnum.END)) {
			for (Token t : tokenList) {

				// Token != START
				if (!(t.getType().equals(TokenTypeEnum.START)) && !(rule.isEmpty())) {
					// Syntax ok
					if (rule.contains(t.getType())) {
						// if(t.getType().equals(TokenTypeEnum.TYPE)) {
						// type = t.getContent();
						// }
						// if(t.getType().equals(TokenTypeEnum.PRINT)) {
						// type = null;
						// }
						// if(type != null &&((t.getType().equals(TokenTypeEnum.CONST_ISSO) &&
						// !type.matches("isso")) || (t.getType().equals(TokenTypeEnum.CONST_WORD) &&
						// !type.matches("word")) || (t.getType().equals(TokenTypeEnum.CONST_ZAL) &&
						// !type.matches("zal"))) ) {
						// logger.error("type error in line " + t.getLine() + ": Got: " +
						// t.getType().getLabel() + " --> Expected: " + type);
						// System.exit(0);
						// }
					}
					// Syntax fail
					else {
						String error = new String();
						for (TokenTypeEnum tte : rule) {
							error = error + tte.getLabel() + "|";
						}
						if (t.getContent().isEmpty()) {
							logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getType().getLabel()
									+ " -->  Expected: " + error.substring(0, (error.length() - 1)));
						} else {
							logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getContent()
									+ " -->  Expected: " + error.substring(0, (error.length() - 1)));
						}
						System.exit(0);
					}
				}
				// Token != START and first token
				else if (!(t.getType().equals(TokenTypeEnum.START)) && rule.isEmpty()) {
					if (t.getContent().isEmpty()) {
						logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getType().getLabel()
								+ " --> Expected: " + TokenTypeEnum.START.getLabel());
					} else {
						logger.error("syntax error in line " + t.getLine() + ": Got: " + t.getContent()
								+ " --> Expected: " + TokenTypeEnum.START.getLabel());
					}
					System.exit(0);
				} else {
					// TODO
				}

				parseItem(t);

				rule = ruleMap.get(t.getType());
			}
		} else {
			if (tokenList.get(tokenList.size() - 1).getContent().isEmpty()) {
				logger.error("syntax error in line " + tokenList.get(tokenList.size() - 1).getLine() + ": Got: "
						+ tokenList.get(tokenList.size() - 1).getType().getLabel() + " --> Expected: "
						+ TokenTypeEnum.END.getLabel());
			} else {
				logger.error("syntax error in line " + tokenList.get(tokenList.size() - 1).getLine() + ": Got: "
						+ tokenList.get(tokenList.size() - 1).getContent() + " --> Expected: "
						+ TokenTypeEnum.END.getLabel());
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
		else if (t.getContent().isEmpty()) {
			logger.debug("Token: " + t.getType());
			parent.setRight(new TreeNode(t.getType(), parent));
			parent = parent.getRight();

			// Node with value
		} else {
			logger.debug("Value: " + t.getContent() + " Token: " + t.getType());
			parent.setRight(new TreeNode(t.getType(), parent));
			parent = parent.getRight();
			parent.setLeft(t.getContent());
		}
	}
}