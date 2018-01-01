package vong.piler.her.vongruntime.virtualmachine.model;

/*
 * Explanation:
 * arg is a value from stack, if arg1, arg2. ..., argn is used, get the last n values from stack.
 */

public enum OperationEnum {
/*00*/    PSA, // push address to stack
/*01*/    PSZ, // push number(zal) to stack
/*02*/    PSI, // push boolean(isso) to stack
/*03*/    PSW,
/*04*/    ADD, // arg1 + arg2
/*05*/    SUB, // arg1 - arg2
/*06*/    MUL, // arg1 * arg2
/*07*/    DIV, // arg1 / arg2
/*08*/    MOD, // arg1 % arg2
/*09*/    LES, // arg1 < arg2
/*10*/    EQL, // arg1 == arg2
/*11*/    NOT, // arg1 != arg2
/*12*/    GTR, // arg1 > arg2
/*13*/    AND, // arg1 && arg2
/*14*/    OHR, // arg1 || arg2
/*15*/    JMP, // jump to address(arg)
/*16*/    JMT, // jump to address(arg) if previous compare was true
/*17*/    PRT, // prints arg
/*18*/    AAL, // prints "hello world!"
/*19*/    SAV,
/*20*/    END,  // end of code
/*21*/    NWL;  // print with newline 
}
