package vong.piler.her.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;
import vong.piler.her.exceptions.WrongNumberOfArgumentsException;
import vong.piler.her.exceptions.WrongOperationException;
import vong.piler.her.steakmachine.OperationEnum;

//TODO: Check type of value
class GeneratorMethods {

	private static Logger logger = LogManager.getLogger(Constants.loggerName);
	private static RegisterHandler registerHandler = RegisterHandler.getInstance();
	
	static List<String> generateCalclations(OperationEnum calculationOperation, List<ValueModel> values){
		List<String> operations = new ArrayList<>();
		try {
			operations.add(registerHandler.addOperation(values.get(0).getOperation(), values.get(0).getValue()));
			values.remove(0);
			values.forEach(value -> {
				try{
					operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
					operations.add(registerHandler.addOperation(calculationOperation));
				} catch (WrongNumberOfArgumentsException wnoae) {
					throw new RuntimeException(wnoae);
				}
			});
		} catch (WrongNumberOfArgumentsException e) {
			logger.error("could not write the operations for an " + calculationOperation, e);
			return null;
		}
		return operations;
	}
	
	static List<String> generateComparator(OperationEnum comparatorOperation, List<ValueModel> values){
		List<String> operations = new ArrayList<>();
		ValueModel prevValue = null;
		try {
			operations.add(registerHandler.addOperation(values.get(0).getOperation(), values.get(0).getValue()));
			values.remove(0);
			operations.add(registerHandler.addOperation(values.get(0).getOperation(), values.get(0).getValue()));
			prevValue = values.get(0);
			values.remove(0);
			operations.add(registerHandler.addOperation(comparatorOperation));
			for (ValueModel value : values){
				operations.add(registerHandler.addOperation(prevValue.getOperation(), prevValue.getValue()));
				operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
				prevValue = value;
				operations.add(registerHandler.addOperation(comparatorOperation));
				operations.add(registerHandler.addOperation(OperationEnum.AND));
			}
		} catch (WrongNumberOfArgumentsException e) {
			logger.error("could not write the operations for an " + comparatorOperation, e);
			return null;
		}
		return operations;
	}
	
	static List<String> generateLinker(OperationEnum linkOperation, List<ValueModel> values){
		List<String> operations = new ArrayList<>();
		try {
			operations.add(registerHandler.addOperation(values.get(0).getOperation(), values.get(0).getValue()));
			values.remove(0);
			values.forEach(value -> {
				try{
					operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
					operations.add(registerHandler.addOperation(linkOperation));
				} catch (WrongNumberOfArgumentsException wnoae) {
					throw new RuntimeException(wnoae);
				}
			});
		} catch (WrongNumberOfArgumentsException e) {
			logger.error("could not write the operations for an " + linkOperation, e);
			return null;
		}
		return operations;
	}
	
	static List<String> generateJump(String address, Boolean isso){
		List<String> operations = new ArrayList<>();
		operations.add(registerHandler.addJumpOperation(address));
		try {
			if (isso != null) {
				operations.add(registerHandler.addOperation(OperationEnum.PSI, isso.toString()));
				operations.add(registerHandler.addOperation(OperationEnum.JMT));
			}
			else {
				operations.add(registerHandler.addOperation(OperationEnum.JMP));
			}
		} catch (WrongNumberOfArgumentsException e) {
			logger.error("could not write the operations for an " + ((isso != null)?OperationEnum.JMT:OperationEnum.JMP), e);
			return null;
		}
		return operations;
	}
	
	static List<String> generateSaveVar(String name, ValueModel value){
		List<String> operations = new ArrayList<>();
		try {
			operations.add(registerHandler.addOperation(OperationEnum.PSA, registerHandler.addVariable(name)+""));
			operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
			switch(value.getOperation()) {
				case PSI:
					operations.add(registerHandler.addOperation(OperationEnum.SVI));
					break;
				/*case PSW:
					operations.add(registerHandler.addOperation(OperationEnum.SVW));
					break;*/
				case PSZ:
					operations.add(registerHandler.addOperation(OperationEnum.SVZ));
					break;
				default:
					throw new WrongOperationException("The Operation " + value.getOperation() + " has no corresponding Operation");
			}
		} catch (WrongNumberOfArgumentsException | WrongOperationException e) {
			logger.error("could not write the operations for a variable save", e);
			return null;
		}

		return operations;
	}
}
