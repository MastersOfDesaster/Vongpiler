package vong.piler.her.generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vong.piler.her.enums.OperationEnum;

public class RegisterHandler {
	
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
	
	int getVariableAddress(String name) {
		if (!dataRegister.containsKey(name)) {
			return addVariable(name);
		}
		return dataRegister.get(name);
	}
	
	String getDataAddress(String name) {
		if (!addressMarkerRegister.containsKey(name)) {
			return name;
		}
		return addressMarkerRegister.get(name)+"";
	}
	
	String getDataAddressOrBlank(String name) {
		if (!addressMarkerRegister.containsKey(name)) {
			return ":X" + name + "X:";
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
	
	void operationAdded() {
		logger.debug("Opeartion will be added to address " + addressPointer);
		addressPointer ++;
	}
}
