package vong.piler.her.generator;

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
	private RegisterHandler registerHandler;
	private ByteCodeWriter writer;
	private int ifCounter;
	private List<Integer> ifGenerated;
	private int tokenId;
	
	public Generator(String outputPath) {
	    this.ifCounter = 0;
	    this.ifGenerated = new ArrayList<>();
	    this.tokenId = 1;
	    this.registerHandler = RegisterHandler.getInstance();
	    this.writer = new ByteCodeWriter(outputPath);
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
		while(!node.getName().equals(TokenTypeEnum.END)) {
			if (node.getName().equals(TokenTypeEnum.VSTART)) {
				node = vStart(node);
			}
			else {
				node = chooseNextStep(node);
			}
		}
		writer.eof();
	}	
	
	public TreeNode chooseNextStep(TreeNode node) throws GenerationsFails {
		switch(node.getName()) {
		case HASHTAG:
			registerHandler.addJumpMarkerIfNotExists(node.getLeft().toString()); 
			return nextNode(node);
		case AAL:
			writer.addAAL();
			return nextNode(node);
		case PRINT:
			return print(node);				
		case NAME:
			return decVarName(node);
		case CMD:
			return cmd(node, null);
		case IFSTART:
			TreeNode nextNode = ifStart(node);
			writer.addCommand(OperationEnum.JMT, "if"+ ifCounter);
			ifCounter++;
			return nextNode;
		case IFEND:
			return ifend(node);
		case GOTOSTART:
			return gotoStart(node);				
		default:
			throw new GenerationsFails(node, tokenId)	;
		}
	}

	public TreeNode vStart(TreeNode node) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.TYPE)) {
			return type(nextNode(node));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode type(TreeNode node) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.NAME)) {
			return decVarName(nextNode(node));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode decVarName(TreeNode node) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.ASSI)) {
			return assi(nextNode(node), node.getLeft().toString());
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode assi(TreeNode node, String name) throws GenerationsFails {
		switch (node.getRight().getName()) {
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
			case CMD:
				return cmd(nextNode(node), name);
			default:
				throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode constIsso(TreeNode node, String name) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel((node.getLeft().equals("yup"))?true:false));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode constIsso(TreeNode node, String name, OperationEnum operation, List<ValueModel> values) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.PNEXT)) {
			values.add(new ValueModel((node.getLeft().equals("yup"))?true:false));
			return pNext(nextNode(node), name, operation, values);
		}
		else if (node.getRight().getName().equals(TokenTypeEnum.PEND)) {
			values.add(new ValueModel((node.getLeft().equals("yup"))?true:false));
			return pEnd(nextNode(node), name, operation, values);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode constWord(TreeNode node, String name) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel(node.getLeft().toString(), false));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode constWord(TreeNode node, String name, OperationEnum operation, List<ValueModel> values) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.PNEXT)) {
			values.add(new ValueModel(node.getLeft().toString(), false));
			return pNext(nextNode(node), name, operation, values);
		}
		else if (node.getRight().getName().equals(TokenTypeEnum.PEND)) {
			values.add(new ValueModel(node.getLeft().toString(), false));
			return pEnd(nextNode(node), name, operation, values);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode constZal(TreeNode node, String name) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel(Double.parseDouble(node.getLeft().toString())));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode constZal(TreeNode node, String name, OperationEnum operation, List<ValueModel> values) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.PNEXT)) {
			values.add(new ValueModel(Double.parseDouble(node.getLeft().toString())));
			return pNext(nextNode(node), name, operation, values);
		}
		else if (node.getRight().getName().equals(TokenTypeEnum.PEND)) {
			values.add(new ValueModel(Double.parseDouble(node.getLeft().toString())));
			return pEnd(nextNode(node), name, operation, values);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode input(TreeNode node, String name) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.VEND)) {
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
		if (node.getRight().getName().equals(TokenTypeEnum.VEND)) {
			return vEnd(nextNode(node), name, new ValueModel(node.getLeft().toString(), true));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode useVarName(TreeNode node, String name, OperationEnum operation, List<ValueModel> values) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.PNEXT)) {
			values.add(new ValueModel(node.getLeft().toString(), true));
			return pNext(nextNode(node), name, operation, values);
		}
		else if (node.getRight().getName().equals(TokenTypeEnum.PEND)) {
			values.add(new ValueModel(node.getLeft().toString(), true));
			return pEnd(nextNode(node), name, operation, values);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode vEnd(TreeNode node, String name, ValueModel value) throws GenerationsFails {
		int address = registerHandler.getVariableAddress(name);
		writer.addCommand(OperationEnum.PSA, address+"");
		writer.addCommand(value.getOperation(), value.getValue());
		writer.addCommand(OperationEnum.SAV);
		return nextNode(node);
	}
	
	private TreeNode vEnd(TreeNode node, String name, OperationEnum operation) throws GenerationsFails {
		int address = registerHandler.addVariable(name);
		writer.addCommand(OperationEnum.PSA, address+"");
		writer.addCommand(operation);
		return nextNode(node);
	}

	private TreeNode print(TreeNode node) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.PSTART)) {
			return pStart(nextNode(node), null, OperationEnum.PRT);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode pStart(TreeNode node, String name, OperationEnum operation) throws GenerationsFails {
		List<ValueModel> values = new ArrayList<>();
		switch (node.getRight().getName()) {
			case CONST_ISSO:
				return constIsso(nextNode(node), name, operation, values);
			case CONST_WORD:
				return constWord(nextNode(node), name, operation, values);
			case CONST_ZAL:
				return constZal(nextNode(node), name, operation, values);
			case NAME:
				return useVarName(nextNode(node), name, operation, values);
			default:
				throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode pNext(TreeNode node, String name, OperationEnum operation, List<ValueModel> values) throws GenerationsFails {
		switch (node.getRight().getName()) {
			case CONST_ISSO:
				return constIsso(nextNode(node), name, operation, values);
			case CONST_WORD:
				return constWord(nextNode(node), name, operation, values);
			case CONST_ZAL:
				return constZal(nextNode(node), name, operation, values);
			case NAME:
				return useVarName(nextNode(node), name, operation, values);
			default:
				throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode pEnd(TreeNode node, String name, OperationEnum operation, List<ValueModel> values) throws GenerationsFails {
		if (name != null) {
			int address = registerHandler.getVariableAddress(name);
			writer.addCommand(OperationEnum.PSA, address+"");
		}
		switch(operation) {
			case PRT:
				PreDefinedFunction.generatePrint(operation, values, writer);
				break;
			case ADD:
			case SUB:
			case MUL:
			case DIV:
			case MOD:
				PreDefinedFunction.generateCalculations(operation, values, writer);
				break;
			case GTR:
			case LES:
			case EQL:
				PreDefinedFunction.generateComparator(operation, values, writer);
				break;
			default:
				throw new GenerationsFails(node, tokenId);
		}
		if (name != null) {
			writer.addCommand(OperationEnum.SAV);
		}
		return nextNode(node);
	}
	
	private TreeNode cmd(TreeNode node, String name) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.FNAME)) {
			return fName(nextNode(node), name);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode fName(TreeNode node, String name) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.PSTART)) {
			OperationEnum operation;
			switch (node.getLeft().toString()) {
				case "sume":
					operation = OperationEnum.ADD;
					break;
				case "abziehung":
					operation = OperationEnum.SUB;
					break;
				case "mahl":
					operation = OperationEnum.MUL;
					break;
				case "teilung":
					operation = OperationEnum.DIV;
					break;
				case "räst":
					operation = OperationEnum.MOD;
					break;
				case "ismär":
					operation = OperationEnum.GTR;
					break;
				case "isweniga":
					operation = OperationEnum.LES;
					break;
				case "same": 
					operation = OperationEnum.EQL;
					break;
				default:
					throw new GenerationsFails(node, tokenId);
			}
			return pStart(nextNode(node), name, operation);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}
	
	private TreeNode gotoStart(TreeNode node) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.HASHTAG)) {
			return hashtag(nextNode(node));
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode hashtag(TreeNode node) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.GOTOEND)) {
			return gotoEnd(nextNode(node), node.getLeft().toString());
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode gotoEnd(TreeNode node, String name) throws GenerationsFails {
		String address = registerHandler.getDataAddress(name);
		if (address == null) {
			throw new GenerationsFails(node, tokenId);
		}
		writer.addCommand(OperationEnum.PSA, address);
		writer.addCommand(OperationEnum.JMP);
		return nextNode(node);
	}
	
	private TreeNode ifStart(TreeNode node) throws GenerationsFails {
		if (node.getRight().getName().equals(TokenTypeEnum.PSTART)) {
			return pStart(nextNode(node), null, OperationEnum.EQL);
		}
		else {
			throw new GenerationsFails(node, tokenId);
		}
	}

	private TreeNode ifend(TreeNode node) {
		int free = findFreeIfCounter();
		registerHandler.addJumpMarkerIfNotExists("IF" + free);
		writer.fillBlankAddress("IF"+free, registerHandler.getDataAddress("IF"+free), 0);
		ifGenerated.add(free);
		return node;
	}
	
	private int findFreeIfCounter() {
		for (int i=ifCounter; i>0; i--) {
			if (!ifGenerated.contains(i))
				return i;
		}
		return -1;
	}
}
