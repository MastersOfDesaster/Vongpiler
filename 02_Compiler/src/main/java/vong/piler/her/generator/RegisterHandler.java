package vong.piler.her.generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import vong.piler.her.steakmachine.OperationEnum;

class RegisterHandler {
	
	private static Logger logger = LogManager.getLogger(RegisterHandler.class);
	
	private static RegisterHandler instance;
	
	private Map<String, Integer> dataRegister;
	private Map<String, Integer> addressMarkerRegister;
	private int addressPointer;
	private int dataPointer;
	
	private RegisterHandler() {
		this.addressPointer = 0;
		this.addressMarkerRegister = new HashMap<>();
		this.dataPointer = 0;
		this.dataRegister = new HashMap<>();
		logger.debug("new RegisterHandler");
	}
	
	static RegisterHandler getInstance() {
		if (instance == null) {
			instance = new RegisterHandler();
		}
		return instance;
	}
	
	int addVariable(String name) {
		if (dataRegister.containsKey(name)) {
			return dataRegister.get(name);
		}
		dataRegister.put(name, dataPointer);
		logger.debug("added Varaiable " + name + " at address " + (dataPointer));
		return dataPointer++;
	}
	
	String getVariableAddress(String name) {
		if (!dataRegister.containsKey(name)) {
			addVariable(name);
		}
		return dataRegister.get(name)+"";
	}
	
	String getDataAddress(String name) {
		if (!addressMarkerRegister.containsKey(name)) {
			addJumpMarkerIfNotExists(name);
		}
		return addressMarkerRegister.get(name)+"";
	}
	
	boolean addJumpMarkerIfNotExists(String name) {
		if (!addressMarkerRegister.containsKey(name)) {
			addressMarkerRegister.put(name, addressPointer);
			return true;
		}
		else {
			return false;
		}
	}
	
	String addOperation(OperationEnum operation) {
		operationAdded(operation);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(operation.ordinal());
		logger.debug("added operation " +  operationBuilder.toString());
		return operationBuilder.toString();
	}
	
	String addOperation(OperationEnum operation, String parameter) {
		if (operation.equals(OperationEnum.PSA) && parameterIsNoNumber(parameter)) {
			parameter = getVariableAddress(parameter);
		}
		operationAdded(operation);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(operation.ordinal());
		operationBuilder.append(" ");
		operationBuilder.append(parameter);
		logger.debug("added operation " +  operationBuilder.toString());
		return operationBuilder.toString();
	}

	String addOperation(OperationEnum operation, int address, int count) {
		operationAdded(operation);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(operation.ordinal());
		operationBuilder.append(" ");
		operationBuilder.append(address);
		operationBuilder.append(" ");
		operationBuilder.append(count);
		logger.debug("added operation " +  operationBuilder.toString());
		return operationBuilder.toString();
	}
	
	String addOperation(OperationEnum operation, String address, int count) {
		return addOperation(operation, getVariableAddress(address), count);
	}
	
	String addJumpOperation(String addressName) {
		operationAdded(OperationEnum.PSA);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(OperationEnum.PSA.ordinal());
		operationBuilder.append(" ");
		if (addressMarkerRegister.containsKey(addressName))
			operationBuilder.append(addressMarkerRegister.get(addressName));
		else
			operationBuilder.append(":X" + addressName + "X:");
		logger.debug("added operation " +  operationBuilder.toString());
		return operationBuilder.toString();
	}
	
	void operationAdded(OperationEnum operation) {
		logger.debug("Opeartion will be added to address " + addressPointer);
		addressPointer ++;
	}
	
	private boolean parameterIsNoNumber(String parameter) {
		try {
			Integer.parseInt(parameter);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

}
