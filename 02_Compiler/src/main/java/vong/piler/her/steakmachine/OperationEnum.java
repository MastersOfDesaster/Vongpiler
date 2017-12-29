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
    EQL(0), // arg1 == arg2
    NQL(0), // arg1 != arg2
    GTR(0), // arg1 > arg2
    AND(0), // arg1 && arg2
    OHR(0), // arg1 || arg2
    JMP(0), // jump to address(arg)
    JMT(0), // jump to address(arg) if previous compare was true
    SAV(0), // save arg in data-memory
    PRT(0), // prints arg
    AAL(0), // prints "hello world!"
    END(0);  // end of code
	
	private int argCount;
	
	OperationEnum(int argCount) {
		this.argCount = argCount;
	}
	
	public int getArgCount() {
		return argCount;
	}
}
