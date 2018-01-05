package vong.piler.her.generator;


import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import vong.piler.her.enums.DataTypeEnum;
import vong.piler.her.enums.OperationEnum;
import vong.piler.her.enums.TokenTypeEnum;
import vong.piler.her.exceptions.GenerationsFails;
import vong.piler.her.generator.model.ValueModel;
import vong.piler.her.parser.TreeNode;

public class Generator {
	
	private static Logger logger = LogManager.getLogger(Generator.class);
	private ByteCodeWriter writer;
	private RegisterHandler registerHandler;
	private int ifCounter;
	private List<Integer> ifGenerated;
	
    public Generator(String outputPath) {
        this.writer = new ByteCodeWriter(new File(outputPath));
        this.registerHandler = RegisterHandler.getInstance();
        this.ifCounter = 0;
        this.ifGenerated = new ArrayList<>();
    }
    
    public void generate(TreeNode root) throws GenerationsFails {
    		TreeNode right = root.getRight();
    		switch(right.getName()) {
			case VSTART:
				variableDeclaration(right);
				break;
			case CMD:
				predefinedFunctionCalls(right, false);
				break;
			case PRINT:
				printFunction(right);
				break;
			case AAL:
				writer.addAAL();
				generate(right);
				break;
			case HASHTAG:
				registerHandler.addJumpMarkerIfNotExists(right.getLeft().toString());
				generate(right);
				break;
			case IFSTART:
				condition(right);
				break;
			case IFEND:
				int free = findFreeIfCounter();
				registerHandler.addJumpMarkerIfNotExists("IF" + free);
				writer.fillBlankAddress("IF"+free, registerHandler.getDataAddress("IF"+free));
				ifGenerated.add(free);
				generate(right);
				break;
			case GOTOSTART:
				jump(right);
				break;
			case NAME:
				if (right.getRight().getRight().getName().equals(TokenTypeEnum.CMD)) {
					varGetFromFunction(right);
				}
				else {
					variableDeclaration(root);
				}
				break;
			case END:
				writer.eof();
				break;
    			default:
    				throw new GenerationsFails("Generation fails at Token" + right);
        }
    }

	private void jump(TreeNode node) throws GenerationsFails {
		TreeNode right = node.getRight();
		writer.addMultiCommand(GeneratorMethods.generateJump(right.getLeft().toString(), false, null));
		right = right.getRight();
		generate(right);
	}

	private void variableDeclaration(TreeNode node) throws GenerationsFails {
		String name = null;
		ValueModel value = null;
		OperationEnum operation = null;
		TreeNode right = null;
		right = node.getRight();
		while (!right.getName().equals(TokenTypeEnum.VEND)) {
			switch (right.getName()) {
				case TYPE:
				case ASSI:
					break;
				case NAME:
					if (name == null) {
						name = (String)right.getLeft();
					}
					else {
						value = new ValueModel(right.getLeft().toString(), true);
					}
					break;
				case CONST_ZAL:
					value = new ValueModel(Double.parseDouble(right.getLeft().toString()));
					break;
				case CONST_ISSO:
					value = new ValueModel((right.getLeft().equals("yup"))?true:false);
					break;
				case CONST_WORD:
					value = new ValueModel(right.getLeft().toString(), false);
					break;
				case INPUT:
					operation = getInputOperation(right);
					break;
				default:
    					throw new GenerationsFails("Generation fails at Token" + right);
			}
			right = right.getRight();
		}
		if (name != null && value != null) {
			writer.addMultiCommand(GeneratorMethods.generateSaveVar(name, value));
		}
		else if (name != null && operation != null) {
			writer.addMultiCommand(GeneratorMethods.generateSaveInput(name, operation));
		}
		else{
			throw new GenerationsFails("Generation fails at Token" + right);
		}
		generate(right);
    }
	
	private OperationEnum getInputOperation(TreeNode node) throws GenerationsFails {
		if (node.getLeft() instanceof DataTypeEnum) {
			switch (((DataTypeEnum)node.getLeft())) {
				case ISSO:
					return OperationEnum.IIN;
				case WORD:
					return OperationEnum.WIN;
				case ZAL:
					return OperationEnum.ZIN;
				default:
					throw new GenerationsFails("Generation fails at Token" + node);
			}
		}
		else{
			throw new GenerationsFails("Generation fails at Token" + node);
		}
	}
	
	private void varGetFromFunction(TreeNode node) throws GenerationsFails {
		String varName = node.getLeft().toString();
		writer.addCommand(OperationEnum.PSA, varName);
		TreeNode right = node.getRight().getRight();
		TreeNode next = predefinedFunctionCalls(right, true);
		writer.addCommand(OperationEnum.SAV);
		generate(next);
	}

	private void printFunction(TreeNode node) throws GenerationsFails {
		List<ValueModel> values = new ArrayList<>();
		while (!node.getName().equals(TokenTypeEnum.PEND) && !node.getName().equals(TokenTypeEnum.VEND)) {
			if (node.getName().equals(TokenTypeEnum.PRINT) || node.getName().equals(TokenTypeEnum.PNEXT)) {
				node = node.getRight();
				values.add(buildParameter(node));
				node = node.getRight();
			}
			else
				throw new GenerationsFails("Generation fails at Token" + node);
		}
		writer.addMultiCommand(GeneratorMethods.generatePrint(values));
		generate(node);
	}

	private void condition(TreeNode node) throws GenerationsFails {
		List<ValueModel> values = new ArrayList<>();
		TreeNode right = node.getRight().getRight();
		if (right.getName().equals(TokenTypeEnum.CONST_ISSO)) {
			values.add(new ValueModel((right.getLeft().toString().equals("yup"))?true:false));
		}
		else if (right.getName().equals(TokenTypeEnum.NAME)) {
			values.add(new ValueModel(right.getLeft().toString(), true));
		}
		else {
			throw new GenerationsFails("First parameter of if has to be CONST_ISSO or NAME");
		}
		right = right.getRight();
		right = right.getRight();
		if (right.getName().equals(TokenTypeEnum.CONST_ISSO)) {
			values.add(new ValueModel((right.getLeft().toString().equals("yup"))?true:false));
		}
		else {
			throw new GenerationsFails("Second parameter of if has to be CONST_ISSO");
		}
		List<String> operationBetween = new ArrayList<>();
		operationBetween.addAll(GeneratorMethods.generateComparator(OperationEnum.EQL, values));
		operationBetween.add(registerHandler.addOperation(OperationEnum.NOT));
		writer.addMultiCommand(GeneratorMethods.generateJump("IF" + ++ifCounter, true, operationBetween));
		right = right.getRight();
		generate(right);
	}
	
    private TreeNode predefinedFunctionCalls(TreeNode node, boolean ignoreNextCall) throws GenerationsFails {
    		OperationEnum operation = null;
		List<ValueModel> values = new ArrayList<>();
		while (!node.getName().equals(TokenTypeEnum.PEND)) {
			node = node.getRight();
			switch (node.getName()) {
				case NAME:
					operation = choosePredefinedOperation(node);
					break;
				case PRINT:
					operation = OperationEnum.PRT;
				case PNEXT:
				case PSTART:
					node = node.getRight();
					values.add(buildParameter(node));
					break;
				case PEND:
					break;
				default:
					throw new GenerationsFails("Generation fails at Token" + node);
			}
		}
		if (operation != null && !values.contains(null)) {
			if (operation.equals(OperationEnum.ADD) || operation.equals(OperationEnum.SUB) || operation.equals(OperationEnum.MUL) 
					|| operation.equals(OperationEnum.DIV) || operation.equals(OperationEnum.MOD)) {
				writer.addMultiCommand(GeneratorMethods.generateCalculations(operation, values));
			}
			else if (operation.equals(OperationEnum.LES) || operation.equals(OperationEnum.GTR) || operation.equals(OperationEnum.EQL)) {
				writer.addMultiCommand(GeneratorMethods.generateComparator(operation, values));
			}
			else{
				throw new GenerationsFails("Generation fails at Token" + node);
			}
		}
		else{
			throw new GenerationsFails("Generation fails at Token" + node);
		}
		if (ignoreNextCall) {
			return node;
		}
		else{
			generate(node);
			return null;
		}
    }
    
	private OperationEnum choosePredefinedOperation(TreeNode node) {
		String method = node.getLeft().toString();
		switch (method) {
			case "sume":
				return OperationEnum.ADD;
			case "abziehung":
				return OperationEnum.SUB;
			case "mahl":
				return OperationEnum.MUL;
			case "teilung":
				return OperationEnum.DIV;
			case "räst":
				return OperationEnum.MOD;
			case "ismär":
				return OperationEnum.GTR;
			case "isweniga":
				return OperationEnum.LES;
			/*case "same": TODO
				return OperationEnum.ADD;TODO*/
		}
		return null;
	}

	private ValueModel buildParameter(TreeNode node) throws GenerationsFails {
		ValueModel value = null;
		switch (node.getName()) {
			case CONST_ZAL:
				value = new ValueModel(Double.parseDouble(node.getLeft().toString()));
				break;
			case CONST_ISSO:
				value = new ValueModel((node.getLeft().toString().equals("yup"))?true:false);
				break;
			case CONST_WORD:
				value = new ValueModel((String)node.getLeft(), false);
				break;
			case NAME:
				value = new ValueModel((String)node.getLeft(), true);
				break;
			default:
				throw new GenerationsFails("Generation fails at Token" + node);
		}
		return value;
	}

	private int findFreeIfCounter() {
		for (int i=ifCounter; i>0; i--) {
			if (!ifGenerated.contains(i))
				return i;
		}
		return -1;
	}
}
