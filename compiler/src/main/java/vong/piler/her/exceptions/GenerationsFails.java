package vong.piler.her.exceptions;

import vong.piler.her.enums.TokenTypeEnum;
import vong.piler.her.parser.TreeNode;

public class GenerationsFails extends Exception {

	private static final long serialVersionUID = -6054019395134165127L;

	public GenerationsFails() {
		super();
	}

	public GenerationsFails(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GenerationsFails(String message, Throwable cause) {
		super(message, cause);
	}

	public GenerationsFails(String message) {
		super(message);
	}

	public GenerationsFails(Throwable cause) {
		super(cause);
	}

	public GenerationsFails(TreeNode node, int tokenId, Throwable cause) {
		super("Generation fails at token " + node.getName() + " with ID " + tokenId, cause);
	}

	public GenerationsFails(TreeNode node, int tokenId) {
		super("Generation fails at token " + node.getName() + " with ID " + tokenId);
	}
	
}
