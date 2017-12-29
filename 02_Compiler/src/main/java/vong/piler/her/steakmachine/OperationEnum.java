package vong.piler.her.steakmachine;

/*
 * Explanation:
 * arg is a value from stack, if arg1, arg2. ..., argn is used, get the last n values from stack.
 */

public enum OperationEnum {
    PSA(1), // push address to stack
    PSZ(1), // push number(zal) to stack
    PSI(1), // push boolean(isso) to stack
    ADD(0), // arg1 + arg2
    SUB(0), // arg1 - arg2
    MUL(0), // arg1 * arg2
    DIV(0), // arg1 / arg2
    MOD(0), // arg1 % arg2
    LES(0), // arg1 < arg2
    EQZ(0), // arg1 == arg2
    EQI(0),
    NQZ(0), // arg1 != arg2
    NQI(0),
    GTR(0), // arg1 > arg2
    AND(0), // arg1 && arg2
    OHR(0), // arg1 || arg2
    JMP(0), // jump to address(arg)
    JMT(0), // jump to address(arg) if previous compare was true
    SAV(0), // save arg in data-memory
    PRT(0), // prints arg
    AAL(0), // prints "hello world!"
    SVA(0),
    SVZ(0),
    SVI(0),
    END(0);  // end of code
	
	private int argCount;
	
	OperationEnum(int argCount) {
		this.argCount = argCount;
	}
	
	public int getArgCount() {
		return argCount;
	}
}
