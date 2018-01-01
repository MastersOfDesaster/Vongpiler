package vong.piler.her.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import vong.piler.her.generator.model.ValueModel;
import vong.piler.her.steakmachine.OperationEnum;

//TODO: Check type of value
class GeneratorMethods {
	
	private static Logger logger = LogManager.getLogger(GeneratorMethods.class);
	private static RegisterHandler registerHandler = RegisterHandler.getInstance();
	
	static List<String> generateCalculations(OperationEnum calculationOperation, List<ValueModel> values){
		List<String> operations = new ArrayList<>();
		operations.add(registerHandler.addOperation(values.get(0).getOperation(), values.get(0).getValue()));
		values.remove(0);
		values.forEach(value -> {
				operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
				operations.add(registerHandler.addOperation(calculationOperation));
		});
		return operations;
	}
	
	static List<String> generateComparator(OperationEnum comparatorOperation, List<ValueModel> values){
		List<String> operations = new ArrayList<>();
		ValueModel prevValue = null;
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
		return operations;
	}
	
	static List<String> generateLinker(OperationEnum linkOperation, List<ValueModel> values){
		List<String> operations = new ArrayList<>();
		operations.add(registerHandler.addOperation(values.get(0).getOperation(), values.get(0).getValue()));
		values.remove(0);
		values.forEach(value -> {
				operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
				operations.add(registerHandler.addOperation(linkOperation));
		});
		return operations;
	}
	
	static List<String> generateJump(String address, boolean isso, List<String> operationsBetween){
		List<String> operations = new ArrayList<>();
		operations.add(registerHandler.addJumpOperation(address));
		if (operationsBetween != null) {
			operations.addAll(operationsBetween);
		}
		if (isso) {
			operations.add(registerHandler.addOperation(OperationEnum.JMT));
		}
		else {
			operations.add(registerHandler.addOperation(OperationEnum.JMP));
		}
		return operations;
	}
	
	static List<String> generateSaveVar(String name, ValueModel value){
		List<String> operations = new ArrayList<>();
		int address = registerHandler.addVariable(name);
		operations.add(registerHandler.addOperation(OperationEnum.PSA, address + ""));
		operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
		operations.add(registerHandler.addOperation(OperationEnum.SAV));
		return operations;
	}

	static List<String> generatePrint(List<ValueModel> values) {
		List<String> operations = new ArrayList<>();
		values.forEach(value -> {
				operations.add(registerHandler.addOperation(value.getOperation(), value.getValue()));
				operations.add(registerHandler.addOperation(OperationEnum.PRT));
		});
		operations.add(registerHandler.addOperation(OperationEnum.NWL));
		return operations;
	}
}
