package vong.piler.her.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;
import vong.piler.her.exceptions.WrongNumberOfArgumentsException;
import vong.piler.her.steakmachine.OperationEnum;

class Calculater {

	private static Logger logger = LogManager.getLogger(Constants.loggerName);
	private static RegisterHandler registerHandler = RegisterHandler.getInstance();
	
	static List<String> addition(List<Integer> constants, List<String> varNames) {
		List<String> operations = new ArrayList<>();
		try {
			if (varNames.isEmpty()) {
				operations.add(registerHandler.addOperation(OperationEnum.PSZ, constants.get(0)));
				constants.remove(0);
			}
			else {
				operations.add(registerHandler.addOperation(OperationEnum.PSA, varNames.get(0)));
				varNames.remove(0);
			}
			generateOperations(OperationEnum.ADD, operations, constants, varNames);
		} catch (WrongNumberOfArgumentsException | RuntimeException e) {
			logger.error("could not write the operations for an addition", e);
			return null;
		}
		return operations;
	}
	
	static List<String> subtraction(int first, List<Integer> constants, List<String> varNames) {
		List<String> operations = new ArrayList<>();
		try {
			operations.add(registerHandler.addOperation(OperationEnum.PSZ, first));
			generateOperations(OperationEnum.SUB, operations, constants, varNames);
		} catch (WrongNumberOfArgumentsException | RuntimeException e) {
			logger.error("could not write the operations for an subtraction", e);
			return null;
		}
		return operations;
	}
	
	static List<String> subtraction(String first, List<Integer> constants, List<String> varNames) {
		List<String> operations = new ArrayList<>();
		try{
			operations.add(registerHandler.addOperation(OperationEnum.PSA, first));
			generateOperations(OperationEnum.SUB, operations, constants, varNames);		
		} catch (WrongNumberOfArgumentsException | RuntimeException e) {
			logger.error("could not write the operations for an subtraction", e);
			return null;
		}
		return operations;
	}
	
	static List<String> multiplication(List<Integer> constants, List<String> varNames) {
		List<String> operations = new ArrayList<>();
		try {
			if (varNames.isEmpty()) {
				operations.add(registerHandler.addOperation(OperationEnum.PSZ, constants.get(0)));
				constants.remove(0);
			}
			else {
				operations.add(registerHandler.addOperation(OperationEnum.PSA, varNames.get(0)));
				varNames.remove(0);
			}
			generateOperations(OperationEnum.MUL, operations, constants, varNames);
		} catch (WrongNumberOfArgumentsException | RuntimeException e) {
			logger.error("could not write the operations for an multiplication", e);
			return null;
		}
		return operations;
	}
	
	static List<String> division(int first, List<Integer> constants, List<String> varNames) {
		List<String> operations = new ArrayList<>();
		try {
			operations.add(registerHandler.addOperation(OperationEnum.PSZ, first));
			generateOperations(OperationEnum.DIV, operations, constants, varNames);
		} catch (WrongNumberOfArgumentsException | RuntimeException e) {
			logger.error("could not write the operations for an subtraction", e);
			return null;
		}
		return operations;
	}
	
	static List<String> division(String first, List<Integer> constants, List<String> varNames) {
		List<String> operations = new ArrayList<>();
		try{
			operations.add(registerHandler.addOperation(OperationEnum.PSA, first));
			generateOperations(OperationEnum.DIV, operations, constants, varNames);		
		} catch (WrongNumberOfArgumentsException | RuntimeException e) {
			logger.error("could not write the operations for an subtraction", e);
			return null;
		}
		return operations;
	}
	
	private static void generateOperations(OperationEnum operation, List<String> operations, List<Integer> constants, List<String> varNames){
		varNames.forEach(varName -> {
			try{
				operations.add(registerHandler.addOperation(OperationEnum.PSA, varName));
				operations.add(registerHandler.addOperation(operation));
			} catch (WrongNumberOfArgumentsException wnoae) {
				throw new RuntimeException(wnoae);
			}
		});
		constants.forEach(constant -> {
			try{
				operations.add(registerHandler.addOperation(OperationEnum.PSZ, constant));
				operations.add(registerHandler.addOperation(operation));
			} catch (WrongNumberOfArgumentsException wnoae) {
				throw new RuntimeException(wnoae);
			}
		});
	}
}
