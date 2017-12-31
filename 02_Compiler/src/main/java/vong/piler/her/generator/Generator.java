package vong.piler.her.generator;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import vong.piler.her.exceptions.GenerationsFails;
import vong.piler.her.generator.model.ValueModel;
import vong.piler.her.lexer.TokenTypeEnum;
import vong.piler.her.logger.LoggerVongManagerHer;
import vong.piler.her.parser.TreeNode;
import vong.piler.her.steakmachine.OperationEnum;

public class Generator {
	
	private static Logger logger = LoggerVongManagerHer.getLogger(Generator.class);
	private String filename;
	private ByteCodeWriter writer;
	private RegisterHandler registerHandler;
	private int ifCounter;
	private List<Integer> ifGenerated;
	
    public Generator(URI sourceUri) {
    		String[] uri = sourceUri.toString().split("/");
        this.filename = uri[uri.length-1];
        this.writer = new ByteCodeWriter(filename);
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
				predefinedFunctionCalls(right);
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
		TreeNode right = node.getRight();
		while (!right.getName().equals(TokenTypeEnum.VEND)) {
			switch (right.getName()) {
				case TYPE:
					break;
				case ASSI:
					break;
				case NAME:
					name = (String)right.getLeft();
					break;
				case CONST_ZAL:
					value = new ValueModel(Double.parseDouble(right.getLeft().toString()));
					break;
				case CONST_ISSO:
					value = new ValueModel((right.getLeft().equals("yup"))?true:false);
					break;
				case CONST_WORD:
					value = new ValueModel((String)right.getLeft(), false);
					break;
				default:
    					throw new GenerationsFails("Generation fails at Token" + right);
			}
			right = right.getRight();
		}
		if (name != null && value != null) {
			writer.addMultiCommand(GeneratorMethods.generateSaveVar(name, value));
		}
		else{
			throw new GenerationsFails("Generation fails at Token" + right);
		}
		generate(right);
    }

	private void printFunction(TreeNode node) throws GenerationsFails {
		List<ValueModel> values = new ArrayList<>();
		while (!node.getName().equals(TokenTypeEnum.PEND)) {
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
	
    private void predefinedFunctionCalls(TreeNode node) throws GenerationsFails {
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
		generate(node);
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
