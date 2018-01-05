package vong.piler.her.generator;

import java.util.ArrayList;
import java.util.List;

import vong.piler.her.enums.OperationEnum;

public class PreDefinedFunction {

	static void generateCalculations(OperationEnum calculationOperation, List<ValueModel> values, ByteCodeWriter writer){
		writer.addCommand(values.get(0).getOperation(), values.get(0).getValue());
		values.remove(0);
		values.forEach(value -> {
			writer.addCommand(value.getOperation(), value.getValue());
			writer.addCommand(calculationOperation);
		});
	}
	
	static void generateComparator(OperationEnum comparatorOperation, List<ValueModel> values, ByteCodeWriter writer){
		ValueModel prevValue = null;
		writer.addCommand(values.get(0).getOperation(), values.get(0).getValue());
		values.remove(0);
		writer.addCommand(values.get(0).getOperation(), values.get(0).getValue());
		prevValue = values.get(0);
		values.remove(0);
		writer.addCommand(comparatorOperation);
		for (ValueModel value : values){
			writer.addCommand(prevValue.getOperation(), prevValue.getValue());
			writer.addCommand(value.getOperation(), value.getValue());
			prevValue = value;
			writer.addCommand(comparatorOperation);
			writer.addCommand(OperationEnum.AND);
		}
	}
	
	static void generatePrint(OperationEnum operation, List<ValueModel> values, ByteCodeWriter writer) {
		values.forEach(value -> {
			writer.addCommand(value.getOperation(), value.getValue());
			writer.addCommand(operation);
		});
		writer.addCommand(OperationEnum.NWL);
	}
}
