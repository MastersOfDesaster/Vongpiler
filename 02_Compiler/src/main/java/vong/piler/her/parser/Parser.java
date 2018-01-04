package vong.piler.her.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import vong.piler.her.enums.DataTypeEnum;
import vong.piler.her.enums.TokenTypeEnum;
import vong.piler.her.lexer.Token;

public class Parser {

	private static Logger logger = LogManager.getLogger(Parser.class);

	private TreeNode parent;
	private TreeNode root;

	Map<TokenTypeEnum, List<TokenTypeEnum>> ruleMap = new EnumMap<TokenTypeEnum, List<TokenTypeEnum>>(
			TokenTypeEnum.class);

	Map<String, DataTypeEnum> dataTypeVariable = new HashMap<String, DataTypeEnum>();
	Map<String, DataTypeEnum> dataTypeFunction = new HashMap<String, DataTypeEnum>();

	public Parser() {
		// Add rules to map
		ruleMap.put(TokenTypeEnum.START, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.VSTART,
				TokenTypeEnum.HASHTAG, TokenTypeEnum.GOTOSTART, TokenTypeEnum.AAL }));
		ruleMap.put(TokenTypeEnum.VSTART, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.TYPE }));
		ruleMap.put(TokenTypeEnum.TYPE, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.NAME }));
		ruleMap.put(TokenTypeEnum.NAME, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.ASSI, TokenTypeEnum.PEND,
				TokenTypeEnum.PNEXT, TokenTypeEnum.PSTART, TokenTypeEnum.VEND }));
		ruleMap.put(TokenTypeEnum.ASSI,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CONST_ISSO, TokenTypeEnum.CONST_WORD,
						TokenTypeEnum.CONST_ZAL, TokenTypeEnum.CMD, TokenTypeEnum.NAME, TokenTypeEnum.INPUT }));
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
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.NAME, TokenTypeEnum.VSTART, TokenTypeEnum.CMD,
						TokenTypeEnum.PRINT, TokenTypeEnum.IFSTART, TokenTypeEnum.GOTOSTART, TokenTypeEnum.GOTOEND,
						TokenTypeEnum.AAL }));
		ruleMap.put(TokenTypeEnum.GOTOSTART, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.HASHTAG }));
		ruleMap.put(TokenTypeEnum.GOTOEND,
				Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.CMD, TokenTypeEnum.PRINT, TokenTypeEnum.AAL,
						TokenTypeEnum.IFSTART, TokenTypeEnum.HASHTAG, TokenTypeEnum.GOTOSTART, TokenTypeEnum.IFEND,
						TokenTypeEnum.END }));
		ruleMap.put(TokenTypeEnum.INPUT, Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.VEND }));
		ruleMap.put(TokenTypeEnum.END, Arrays.asList(new TokenTypeEnum[] {}));

		// Define data type for functions
		dataTypeVariable.put("sume", DataTypeEnum.ZAL); // Addition(+)
		dataTypeVariable.put("abziehung", DataTypeEnum.ZAL); // Subtraktion(-)
		dataTypeVariable.put("mahl", DataTypeEnum.ZAL); // Multiplikation(*)
		dataTypeVariable.put("teilung", DataTypeEnum.ZAL); // Division(/)
		dataTypeVariable.put("räst", DataTypeEnum.ZAL); // Modulo(%)
		dataTypeVariable.put("ismär", DataTypeEnum.ISSO); // Größer als(>)
		dataTypeVariable.put("isweniga", DataTypeEnum.ISSO); // Kleiner als(<)
		dataTypeVariable.put("same", DataTypeEnum.ISSO);// Und(&&)
	}

	public TreeNode parse(List<Token> tokenList) {

		List<TokenTypeEnum> rule = new ArrayList<TokenTypeEnum>();

		String type = null;
		boolean vStart = false;
		boolean bistDu = false;

		// Check if the last token == END
		if (tokenList.get(tokenList.size() - 1).getType().equals(TokenTypeEnum.END)) {
			for (Token t : tokenList) {
				// Token != START
				if (!(t.getType().equals(TokenTypeEnum.START)) && !(rule.isEmpty())) {
					// Syntax check from ruleMap == ok
					if (rule.contains(t.getType())) {
						// Check type
						switch (t.getType()) {
						case TYPE:
							type = t.getContent();
							break;
						case NAME:
							if (vStart) {
								setDataTypeVariable(t.getContent(), type, t.getLine());
							} else if (parent.getName().equals(TokenTypeEnum.CMD)) {
								System.out.println("CMD");
								System.out.println("#" + t.getContent() + "#");
								System.out.println(dataTypeFunction.get(t.getContent()));

							}
							break;
						case VSTART:
							vStart = true;
							break;
						case VEND:
							vStart = false;
							type = null;
							break;
						case CONST_ISSO:
						case CONST_WORD:
						case CONST_ZAL:
							// Check data type for assignment after initialization
							if (!vStart && parent.getName().equals(TokenTypeEnum.ASSI)
									&& !dataTypeVariable.get(parent.getParent().getLeft())
											.equals(getDataTypeEnum(t.getType().getLabel()))) {
								// TODO
								error(t, "Tipe",
										Arrays.asList(
												new TokenTypeEnum[] { TokenTypeEnum.CONST_ZAL, TokenTypeEnum.INPUT }),
										null);
							}
							// Test data type for assignment during initialization
							else if (vStart && !dataTypeVariable.get(parent.getParent().getLeft())
									.equals(getDataTypeEnum(t.getType().getLabel()))) {
								error(t, "Tipe", Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.INPUT }),
										dataTypeVariable.get(parent.getParent().getLeft()));
							}
							break;
						case IFSTART:
							bistDu = true;
							break;
						}
					}
					// Syntax fail
					else {
						error(t, "Sintax", rule, null);
					}
				}
				// Token != START and first token
				else if (!(t.getType().equals(TokenTypeEnum.START)) && rule.isEmpty()) {
					error(t, "Sintax", Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.START }), null);
				} else {
					// TODO
				}

				// Bist du
				if (bistDu && t.getType().equals(TokenTypeEnum.IFSTART)) {
					Token hT = new Token(0, TokenTypeEnum.PSTART);
					parseItem(t);
					parseItem(hT);
					rule = ruleMap.get(TokenTypeEnum.PSTART);
				} else if (bistDu && t.getType().equals(TokenTypeEnum.CONST_ISSO)) {
					Token hT = new Token(0, TokenTypeEnum.PEND);
					parseItem(t);
					parseItem(hT);
					rule = ruleMap.get(TokenTypeEnum.PEND);
					bistDu = false;
				} else {
					parseItem(t);
					rule = ruleMap.get(t.getType());
				}
			}
		}
		// Last token is != END
		else {
			error(tokenList.get(tokenList.size() - 1), "Sintax",
					Arrays.asList(new TokenTypeEnum[] { TokenTypeEnum.END }), null);
		}
		return root;
	}

	private DataTypeEnum getDataTypeEnum(String type) {
		return DataTypeEnum.valueOf(type.substring(6).toUpperCase());
	}

	private void setDataTypeVariable(String name, String type, int line) {
		// check if variable name is already assigned
		if (!dataTypeVariable.containsKey(name)) {
			switch (type) {
			case "isso":
				dataTypeVariable.put(name, DataTypeEnum.ISSO);
				break;
			case "word":
				dataTypeVariable.put(name, DataTypeEnum.WORD);
				break;
			case "zal":
				dataTypeVariable.put(name, DataTypeEnum.ZAL);
				break;
			}
		} else {
			logger.error("Fehler voms doppelteng variablemameng her! Du lauch!!! Schausd du Zeile " + line + ": \""
					+ name + "\" gibd es bereits!");
			System.exit(0);
		}
	}

	private void error(Token t, String message, List<TokenTypeEnum> rule, DataTypeEnum type) {
		String error = new String();
		for (TokenTypeEnum tte : rule) {
			error = error + tte.getLabel() + "|";
		}
		if (t.getContent().isEmpty()) {
			logger.error("Fehler voms " + message + " her! Du lauch!!! Schausd du Zeile " + t.getLine() + ": Hab: "
					+ t.getType().getLabel() + " --> Gieb: " + error.substring(0, (error.length() - 1)));
		} else {
			logger.error("Fehler voms " + message + " her! Du lauch!!! Schausd du Zeile " + t.getLine() + ": Hab: "
					+ t.getContent() + " --> Gieb: " + error.substring(0, (error.length() - 1)));
		}
		System.exit(0);
	}

	private void parseItem(Token t) {

		// Root node
		if (root == null) {
			logger.debug("Token: " + t.getType());
			root = new TreeNode(t.getType(), null);
			parent = root;
		} else if (t.getType().equals(TokenTypeEnum.INPUT)) {
			// TODO: some is null: logger.debug("Value: " +
			// parent.getParent().getParent().getLeft().toString() + " Token: " +
			// t.getType());
			parent.setRight(new TreeNode(t.getType(), parent));
			parent = parent.getRight();
			parent.setLeft(dataTypeVariable.get(parent.getParent().getParent().getLeft().toString()));
		}
		// Node without value
		else if (t.getContent().isEmpty()) {
			logger.debug("Token: " + t.getType());
			parent.setRight(new TreeNode(t.getType(), parent));
			parent = parent.getRight();
		} else {
			logger.debug("Value: " + t.getContent() + " Token: " + t.getType());
			parent.setRight(new TreeNode(t.getType(), parent));
			parent = parent.getRight();
			parent.setLeft(t.getContent());
		}
	}
}