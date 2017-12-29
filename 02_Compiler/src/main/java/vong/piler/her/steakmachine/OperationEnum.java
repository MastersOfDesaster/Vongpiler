package vong.piler.her.steakmachine;

/*
 * Explanation:
 * arg is a value from stack, if arg1, arg2. ..., argn is used, get the last n values from stack.
 */

public enum OperationEnum {
    PSA, // push address to stack
    PSZ, // push number(zal) to stack
    PSI, // push boolean(isso) to stack
    ADD, // arg1 + arg2
    SUB, // arg1 - arg2
    MUL, // arg1 * arg2
    DIV, // arg1 / arg2
    MOD, // arg1 % arg2
    LES, // arg1 < arg2
    EQL, // arg1 == arg2
    NQL, // arg1 != arg2
    GTR, // arg1 > arg2
    AND, // arg1 && arg2
    OHR, // arg1 || arg2
    JMP, // jump to address(arg)
    JMT, // jump to address(arg) if previous compare was true
    SAV, // save arg in data-memory
    PRT, // prints arg
    AAL, // prints "hello world!"
    END  // end of code
}
