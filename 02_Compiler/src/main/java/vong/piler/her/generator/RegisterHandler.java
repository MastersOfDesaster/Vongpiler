package vong.piler.her.generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import vong.piler.her.exceptions.WrongNumberOfArgumentsException;
import vong.piler.her.logger.LoggerVongManagerHer;
import vong.piler.her.steakmachine.OperationEnum;

class RegisterHandler {
	
	private static Logger logger = LoggerVongManagerHer.getLogger(RegisterHandler.class);
	
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
	
	String addOperation(OperationEnum operation) throws WrongNumberOfArgumentsException {
		if (operation.getArgCount() != 0)
			throw new WrongNumberOfArgumentsException(operation + " has " + operation.getArgCount() + " arguments instead of 0");
		operationAdded(operation);
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(operation.ordinal());
		logger.debug("added operation " +  operationBuilder.toString());
		return operationBuilder.toString();
	}
	
	String addOperation(OperationEnum operation, String parameter) throws WrongNumberOfArgumentsException {
		if (operation.getArgCount() != 1)
			throw new WrongNumberOfArgumentsException(operation + " has " + operation.getArgCount() + " arguments instead of 1");
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
		logger.debug("added operation " +  operationBuilder.toString());
		return operationBuilder.toString();
	}
	
	String addOperation(OperationEnum operation, String address, int count) throws WrongNumberOfArgumentsException {
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
		addressPointer += (operation.getArgCount() + 1);
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
