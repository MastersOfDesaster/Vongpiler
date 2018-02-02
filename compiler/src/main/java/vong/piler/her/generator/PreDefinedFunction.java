package vong.piler.her.generator;

import java.util.ArrayList;
import java.util.List;

import vong.piler.her.enums.OperationEnum;

public class PreDefinedFunction {

	static void generateCalculations(OperationEnum calculationOperation, List<ValueModel> values, ByteCodeWriter writer){
		writer.addCommandResolveAdresses(values.get(0).getOperation(), values.get(0).getValue());
		values.remove(0);
		values.forEach(value -> {
			writer.addCommandResolveAdresses(value.getOperation(), value.getValue());
			writer.addCommand(calculationOperation);
		});
	}
	
	static void generateComparator(OperationEnum comparatorOperation, List<ValueModel> values, ByteCodeWriter writer){
		ValueModel prevValue = null;
		writer.addCommandResolveAdresses(values.get(0).getOperation(), values.get(0).getValue());
		values.remove(0);
		writer.addCommandResolveAdresses(values.get(0).getOperation(), values.get(0).getValue());
		prevValue = values.get(0);
		values.remove(0);
		writer.addCommand(comparatorOperation);
		for (ValueModel value : values){
			writer.addCommandResolveAdresses(prevValue.getOperation(), prevValue.getValue());
			writer.addCommandResolveAdresses(value.getOperation(), value.getValue());
			prevValue = value;
			writer.addCommand(comparatorOperation);
			writer.addCommand(OperationEnum.AND);
		}
	}
	
	static void generateLogicOperator(OperationEnum logicOperation, List<ValueModel> values, ByteCodeWriter writer){
		writer.addCommandResolveAdresses(values.get(0).getOperation(), values.get(0).getValue());
		values.remove(0);
		values.forEach(value -> {
			writer.addCommandResolveAdresses(value.getOperation(), value.getValue());
			writer.addCommand(logicOperation);
		});
	}
	
	static void generatePrint(OperationEnum operation, List<ValueModel> values, ByteCodeWriter writer) {
		values.forEach(value -> {
			writer.addCommandResolveAdresses(value.getOperation(), value.getValue());
			writer.addCommand(operation);
		});
		writer.addCommand(OperationEnum.NWL);
	}
}
