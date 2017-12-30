package vong.piler.her.vongruntime.virtualmachine.model;

/*
 * Explanation:
 * arg is a value from stack, if arg1, arg2. ..., argn is used, get the last n values from stack.
 */

public enum OperationEnum {
/*00*/    PSA(1), // push address to stack
/*01*/    PSZ(1), // push number(zal) to stack
/*02*/    PSI(1), // push boolean(isso) to stack
/*03*/    PSW(1),
/*04*/    ADD(0), // arg1 + arg2
/*05*/    SUB(0), // arg1 - arg2
/*06*/    MUL(0), // arg1 * arg2
/*07*/    DIV(0), // arg1 / arg2
/*08*/    MOD(0), // arg1 % arg2
/*09*/    LES(0), // arg1 < arg2
/*10*/    EQZ(0), // arg1 == arg2
/*11*/    EQI(0),
/*12*/    NQZ(0), // arg1 != arg2
/*13*/    NQI(0),
/*14*/    GTR(0), // arg1 > arg2
/*15*/    AND(0), // arg1 && arg2
/*16*/    OHR(0), // arg1 || arg2
/*17*/    JMP(0), // jump to address(arg)
/*18*/    JMT(0), // jump to address(arg) if previous compare was true
/*19*/    PRT(0), // prints arg
/*20*/    AAL(0), // prints "hello world!"
/*21*/    SVA(0),
/*22*/    SVZ(0),
/*23*/    SVI(0),
/*24*/    SVW(0),
/*25*/    EQW(0),
/*26*/    NQW(0),
/*27*/    END(0);  // end of code
	
	private int argCount;
	
	OperationEnum(int argCount) {
		this.argCount = argCount;
	}
	
	public int getArgCount() {
		return argCount;
	}
}
