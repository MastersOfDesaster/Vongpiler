package vong.piler.her.generator;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;
import vong.piler.her.exceptions.GenerationsFails;
import vong.piler.her.generator.model.ValueModel;
import vong.piler.her.lexer.TokenTypeEnum;
import vong.piler.her.logger.LoggerVongManagerHer;
import vong.piler.her.parser.TreeNode;
import vong.piler.her.steakmachine.OperationEnum;

public class Generator {
	
	private static Logger logger = LoggerVongManagerHer.getLogger(Generator.class);
	String filename;
	ByteCodeWriter writer;
	
    public Generator(URI sourceUri) {
    		String[] uri = sourceUri.toString().split("/");
        this.filename = uri[uri.length-1];
        this.writer = new ByteCodeWriter(filename);
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
			case END:
				writer.addPrt();
				writer.eof();
				break;
    			default:
    				throw new GenerationsFails("Generation fails at Token" + right);
        }
    }
    
    private void variableDeclaration(TreeNode var) throws GenerationsFails {
		String name = null;
		ValueModel value = null;
		var = var.getRight();
		while (!var.getName().equals(TokenTypeEnum.VEND)) {
			switch (var.getName()) {
				case TYPE:
					break;
				case ASSI:
					break;
				case NAME:
					name = (String)var.getLeft();
					break;
				case CONST_ZAL:
					value = new ValueModel(Double.parseDouble(var.getLeft().toString()));
					break;
				case CONST_ISSO:
					value = new ValueModel((var.getLeft().equals("jup"))?true:false);
					break;
				case CONST_WORD:
					value = new ValueModel((String)var.getLeft(), false);
					break;
				default:
    					throw new GenerationsFails("Generation fails at Token" + var);
			}
			var = var.getRight();
		}
		if (name != null && value != null) {
			writer.addMultiCommand(GeneratorMethods.generateSaveVar(name, value));
		}
		else{
			throw new GenerationsFails("Generation fails at Token" + var);
		}
		generate(var);
    }
    
    private void predefinedFunctionCalls(TreeNode var) throws GenerationsFails {
    		OperationEnum operation = null;
		List<ValueModel> values = new ArrayList<>();
		while (!var.getName().equals(TokenTypeEnum.PEND)) {
			var = var.getRight();
			switch (var.getName()) {
				case NAME:
					operation = choosePredefinedOperation(var);
					break;
				case PNEXT:
				case PSTART:
					values.add(buildParameter(var));
					var = var.getRight(); //TODO: this is a workaround
					break;
				case PEND:
					break;
				default:
					throw new GenerationsFails("Generation fails at Token" + var);
			}
		}
		if (operation != null && !values.contains(null)) {
			if (operation.equals(OperationEnum.ADD) || operation.equals(OperationEnum.SUB) || operation.equals(OperationEnum.MUL) 
					|| operation.equals(OperationEnum.DIV) || operation.equals(OperationEnum.MOD)) {
				writer.addMultiCommand(GeneratorMethods.generateCalculations(operation, values));
			}
			else if (operation.equals(OperationEnum.LES) || operation.equals(OperationEnum.GTR) || operation.equals(OperationEnum.EQI) 
					|| operation.equals(OperationEnum.EQZ) || operation.equals(OperationEnum.EQW) || operation.equals(OperationEnum.NQW)
					|| operation.equals(OperationEnum.NQI) || operation.equals(OperationEnum.NQZ)) {
				writer.addMultiCommand(GeneratorMethods.generateComparator(operation, values));
			}
			else{
				throw new GenerationsFails("Generation fails at Token" + var);
			}
		}
		else{
			throw new GenerationsFails("Generation fails at Token" + var);
		}
		//generate(var);
		writer.addPrt();
		writer.eof();
    }

	private OperationEnum choosePredefinedOperation(TreeNode var) {
		String method = var.getLeft().toString();
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

	private ValueModel buildParameter(TreeNode var) throws GenerationsFails {
		var = var.getRight();
		ValueModel value = null;
		switch (var.getName()) {
			case CONST_ZAL:
				value = new ValueModel(Double.parseDouble(var.getLeft().toString()));
				break;
			case CONST_ISSO:
				value = new ValueModel((var.getLeft().equals("jup"))?true:false);
				break;
			case CONST_WORD:
				value = new ValueModel((String)var.getLeft(), false);
				break;
			case NAME:
				value = new ValueModel((String)var.getLeft(), true);
				break;
			default:
				throw new GenerationsFails("Generation fails at Token" + var);
		}
		var = var.getRight();
		return value;
	}
}

//"sume" | "abziehung" | "mahl" | "teilung" | "räst" | "ismär" | "isweniga" | "same"
/*private void testPrint(TreeNode root, int tabCount) {
    		System.out.println("\t\t" + root.getName());
    		leftPrint(root.getLeft(), tabCount-1);
    		if (root.getRight() != null)
    			testPrint(root.getRight(), ++tabCount);
    }
    
    private void leftPrint(Object left, int tabCount) {
    		for (int i = 0; i < tabCount; i++) {
    			System.out.print("\t");
		}
    		if (left == null)
    			System.out.print("null");
    		else if (left instanceof Double) 
			System.out.print(((Double) left));
		else if (left instanceof Boolean) 
			System.out.print(((Boolean) left));
		else if (left instanceof String) 
			System.out.print(((String) left));
		else 
			System.out.print(left.toString());
    }*/
