package vong.piler.her.steakmachine;


public class Command {
	private OperationEnum opCode;
	private Object firstParam, secondParam;

	public OperationEnum getOpCode() {
		return opCode;
	}

	public void setOpCode(OperationEnum opCode) {
		this.opCode = opCode;
	}

	public Object getFirstParam() {
		return firstParam;
	}

	public void setFirstParam(Object firstParam) {
		this.firstParam = firstParam;
	}

	public Object getSecondParam() {
		return secondParam;
	}

	public void setSecondParam(Object secondParam) {
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

}
