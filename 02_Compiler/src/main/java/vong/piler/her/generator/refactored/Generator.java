package vong.piler.her.generator.refactored;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vong.piler.her.enums.DataTypeEnum;
import vong.piler.her.enums.OperationEnum;
import vong.piler.her.enums.TokenTypeEnum;
import vong.piler.her.exceptions.GenerationsFails;
import vong.piler.her.parser.TreeNode;

public class Generator {
	
	private static Logger logger = LogManager.getLogger(Generator.class);
	private int ifCounter;
	private List<Integer> ifGenerated;
	private int tokenId;
	
	public Generator(String outputPath) {
	    this.ifCounter = 0;
	    this.ifGenerated = new ArrayList<>();
	    this.tokenId = 1;
	}
	
	public TreeNode nextNode(TreeNode node) {
		tokenId++;
		return node.getRight();
	}

	public void start(TreeNode root) throws GenerationsFails {
		if (!root.getName().equals(TokenTypeEnum.START)) {
			throw new GenerationsFails(root, tokenId);
		}
		TreeNode node = nextNode(root);
		if (node.getName().equals(TokenTypeEnum.VSTART)) {
			node = vStart(node);
		}
		else {
			node = chooseNextStep(node);
		}
		if (!node.getName().equals(TokenTypeEnum.END)) {
			throw new GenerationsFails(root, tokenId);
		}
		//EOF
	}
	
	public TreeNode vStart(TreeNode node) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.TYPE)) {
			return type(nextNode(node));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode type(TreeNode node) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.NAME)) {
			return decVarName(nextNode(node));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode decVarName(TreeNode node) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.ASSI)) {
			return assi(nextNode(node), node.getLeft().toString());
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode assi(TreeNode node, String name) throws GenerationsFails {
		switch (node.getName()) {
			case CONST_ISSO:
				return constIsso(nextNode(node), name);				
			case CONST_WORD:
				return constWord(nextNode(node), name);				
			case CONST_ZAL:
				return constZal(nextNode(node), name);				
			case INPUT:
				return input(nextNode(node), name);				
			case NAME:
				return useVarName(nextNode(node), name);				
			default:
				throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode constIsso(TreeNode node, String name) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel((node.getLeft().equals("yup"))?true:false));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode constWord(TreeNode node, String name) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel(node.getLeft().toString(), false));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode constZal(TreeNode node, String name) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel(Double.parseDouble(node.getLeft().toString())));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode input(TreeNode node, String name) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.VEND)) {
			if (node.getLeft() instanceof DataTypeEnum) {
				switch (((DataTypeEnum)node.getLeft())) {
					case ISSO:
						return vEnd(nextNode(node), name, OperationEnum.IIN);
					case WORD:
						return vEnd(nextNode(node), name,  OperationEnum.WIN);
					case ZAL:
						return vEnd(nextNode(node), name,  OperationEnum.ZIN);
				}
			}
		}
		throw new GenerationsFails(node, tokenId);
	}

	private TreeNode useVarName(TreeNode node, String name) throws GenerationsFails {
		if (node.getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel(node.getLeft().toString(), true));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode vEnd(TreeNode node, String name, ValueModel value) throws GenerationsFails {
		return null;
	}
	
	private TreeNode vEnd(TreeNode node, String name, OperationEnum operation) throws GenerationsFails {
		return null;
	}

	public TreeNode chooseNextStep(TreeNode node) throws GenerationsFails {
		
		return null;
	}
}
