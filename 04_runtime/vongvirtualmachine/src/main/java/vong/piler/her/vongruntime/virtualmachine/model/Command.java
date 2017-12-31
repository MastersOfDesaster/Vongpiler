package vong.piler.her.vongruntime.virtualmachine.model;

public class Command {
	private OperationEnum opCode;
	private String firstParam, secondParam;

	public OperationEnum getOpCode() {
		return opCode;
	}

	public void setOpCode(OperationEnum opCode) {
		this.opCode = opCode;
	}

	public String getFirstParam() {
		return firstParam;
	}

	public void setFirstParam(String firstParam) {
		this.firstParam = firstParam;
	}

	public String getSecondParam() {
		return secondParam;
	}

	public void setSecondParam(String secondParam) {
		this.secondParam = secondParam;
	}

	public int paramcount() {
		if(firstParam != null && secondParam != null) {
			return 2;
		}else if (firstParam != null && secondParam == null) {
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", opCode, firstParam == null? "" : firstParam , secondParam == null?"": secondParam);
	}
	
	

}
