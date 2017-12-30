
package vong.piler.her.generator;

import java.util.HashMap;
import java.util.Map;

import vong.piler.her.exceptions.WrongNumberOfArgumentsException;
import vong.piler.her.steakmachine.OperationEnum;

class RegisterHandler {
	
	private static RegisterHandler instance;
	
	private Map<String, Integer> dataRegister;
	private Map<String, Integer> addressMarkerRegister;
	private int addressPointer;
	private int dataPointer;
	
	private RegisterHandler() {
		this.addressPointer = 0;
		this.addressMarkerRegister = new HashMap<>();
		this.dataRegister = new HashMap<>();
	}
	
	static RegisterHandler getInstance() {
		if (instance == null) {
			instance = new RegisterHandler();
		}
		return instance;
	}
	
	int addVariable(String name) {
		dataRegister.put(name, dataPointer++);
		return dataPointer;
	}
	
	String getVariableAddress(String name) {
		if (!dataRegister.containsKey(name)) {
			addVariable(name);
		}
		return dataRegister.get(name)+"";
	}
	
	void addJumpMarker(String name) {
		addressMarkerRegister.put(name, addressPointer);
	}
	
	String addOperation(OperationEnum operation) throws WrongNumberOfArgumentsException {
		if (operation.getArgCount() != 0)
			throw new WrongNumberOfArgumentsException(operation + " has " + operation.getArgCount() + " arguments instead of 0");
		operationAdded(operation);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(operation.ordinal());
		return operationBuilder.toString();
	}
	
	String addOperation(OperationEnum operation, String parameter) throws WrongNumberOfArgumentsException {
		if (operation.getArgCount() != 1)
			throw new WrongNumberOfArgumentsException(operation + " has " + operation.getArgCount() + " arguments instead of 1");
		if (operation.equals(OperationEnum.PSA)) {
			parameter = getVariableAddress(parameter);
		}
		operationAdded(operation);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(operation.ordinal());
		operationBuilder.append(" ");
		operationBuilder.append(parameter);
		return operationBuilder.toString();
	}
	
	String addOperation(OperationEnum operation, int address, int count) throws WrongNumberOfArgumentsException {
		if (operation.getArgCount() != 2)
			throw new WrongNumberOfArgumentsException(operation + " has " + operation.getArgCount() + " arguments instead of 2");
		operationAdded(operation);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(operation.ordinal());
		operationBuilder.append(" ");
		operationBuilder.append(address);
		operationBuilder.append(" ");
		operationBuilder.append(count);
		return operationBuilder.toString();
	}
	
	String addOperation(OperationEnum operation, String address, int count) throws WrongNumberOfArgumentsException {
		return addOperation(operation, getVariableAddress(address), count);
	}
	
	String addJumpOperation(String address) {
		operationAdded(OperationEnum.PSA);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(OperationEnum.PSA.ordinal());
		operationBuilder.append(" ");
		operationBuilder.append(addressMarkerRegister.get(address));
		return operationBuilder.toString();
	}
	
	void operationAdded(OperationEnum operation) {
		addressPointer += operation.getArgCount() + 1;
	}

}
