package vong.piler.her.parser;

import vong.piler.her.enums.TokenTypeEnum;

public class TreeNode {

	private TokenTypeEnum name;

	private TreeNode parent;

	private TreeNode right;

	private Object left;

	public TreeNode(TokenTypeEnum name, TreeNode parent) {
		this.name = name;
		this.parent = parent;
	}

	public TokenTypeEnum getName() {
		return name;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public Object getLeft() {
		return this.left;
	}

	public TreeNode getRight() {
		return right;
	}
}