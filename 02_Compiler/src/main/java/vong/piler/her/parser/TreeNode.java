package vong.piler.her.parser;

import vong.piler.her.lexer.TokenTypeEnum;

public class TreeNode {
	
	private TokenTypeEnum name;
    
    private TreeNode right;// = new TreeNode(null);

    private Object left;
    
    public TreeNode(TokenTypeEnum name) {
    	this.name = name;
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