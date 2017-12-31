package vong.piler.her.vongruntime.virtualmachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Stack;

import vong.piler.her.vongruntime.virtualmachine.model.StackElement.Type;
import vong.piler.her.vongruntime.exception.EmptyLineException;
import vong.piler.her.vongruntime.exception.InstructionPointerOutOfBoundsException;
import vong.piler.her.vongruntime.exception.ReadEmptyRegisterException;
import vong.piler.her.vongruntime.exception.UnknownCommandException;
import vong.piler.her.vongruntime.exception.UnsupportedNumberofArgumentsException;
import vong.piler.her.vongruntime.util.StringUtils;
import vong.piler.her.vongruntime.virtualmachine.model.Command;
import vong.piler.her.vongruntime.virtualmachine.model.OperationEnum;
import vong.piler.her.vongruntime.virtualmachine.model.StackElement;

public class Steakmachine {

	private PrintStream standardOut = System.out;
	private PrintStream errorOut = System.err;
	private boolean debugOutput = false;
	private boolean readAssembler = false;
	
	private static final int PROGRAM_MEMORY_SIZE = 100;
	private static final int CODE_MEMORY_SIZE = 100;
	private Stack<StackElement> stack;
	private StackElement[] programmMemory;
	private String[] codeMemory;

	private int instructionPointer;
	private boolean running;
	
	
	public static int getProgramMemorySize() {
		return PROGRAM_MEMORY_SIZE;
	}



	public static int getCodeMemorySize() {
		return CODE_MEMORY_SIZE;
	}

	
	


	public boolean isDebugOutput() {
		return debugOutput;
	}



	public void setDebugOutput(boolean debugOutput) {
		this.debugOutput = debugOutput;
	}



	public boolean isReadAssembler() {
		return readAssembler;
	}



	public void setReadAssembler(boolean readAssembler) {
		this.readAssembler = readAssembler;
	}



	public PrintStream getStandardOut() {
		return standardOut;
	}



	public void setStandardOut(PrintStream standardOut) {
		this.standardOut = standardOut;
	}



	public PrintStream getErrorOut() {
		return errorOut;
	}



	public void setErrorOut(PrintStream errorOut) {
		this.errorOut = errorOut;
	}



	public void load(File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				codeMemory[i] = line;
				i++;
			}
		} catch (IOException e) {
			printErrorOutput("Could not read file at: " + file.getAbsolutePath());
		} catch (ArrayIndexOutOfBoundsException e) {
			printErrorOutput("Programmcode doesn't fit in programmregister which supports " + CODE_MEMORY_SIZE + " commands");
		}
	}
	
	

	public void init() {
		stack = new Stack<StackElement>();
		codeMemory = new String[CODE_MEMORY_SIZE];
		programmMemory = new StackElement[PROGRAM_MEMORY_SIZE];
		instructionPointer = 0;
	}

	public void run() {
		running = true;
		while (running) {
			try {
				String rawCommand = readCommand();
				instructionPointer++;
				Command command = decodeCommand(rawCommand);
				executeCommand(command);
			} catch (UnsupportedNumberofArgumentsException e) {
				printErrorOutput("Wrong number of arguments submitted for operation");
				running = false;
			} catch (InstructionPointerOutOfBoundsException e) {
				printErrorOutput("Instruction-pointer ran out of bounds");
				running = false;
			} catch (UnknownCommandException e) {
				printErrorOutput(String.format("Encountered unknown command: %s", e.getMessage()));
				running = false;
			} catch (EmptyLineException e) {
				printErrorOutput("Encountered empty line in codefile");
				running = false;
			} catch (ReadEmptyRegisterException e) {
				printErrorOutput("instruction-pointer at empty register");
				running = false;
			} 
		}

	}

	private String readCommand() throws InstructionPointerOutOfBoundsException, ReadEmptyRegisterException {
		try {
			String rawCommand = codeMemory[instructionPointer];
			if(rawCommand == null) {
				throw new ReadEmptyRegisterException();
			}
			printDebugOutput(String.format("Read raw command: %s", rawCommand));
			return rawCommand;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InstructionPointerOutOfBoundsException();
		}

	}

	private Command decodeCommand(String rawCommand) throws UnsupportedNumberofArgumentsException, EmptyLineException, UnknownCommandException {
		Command command = new Command();
		String[] commandParts = rawCommand.split(" ");
		if(commandParts.length == 0) {
			throw new EmptyLineException();
		}
		
		
		if(readAssembler) {
			command.setOpCode(OperationEnum.valueOf(commandParts[0]));			
		}else {
			try {
				command.setOpCode(OperationEnum.values()[Integer.parseInt(commandParts[0])]);	
			}catch(ArrayIndexOutOfBoundsException e) {
				throw new UnknownCommandException(commandParts[0]);
			}
			
		}
		
		if(commandParts.length == 2) {
			command.setFirstParam(commandParts[1]);
		}else if(commandParts.length == 3) {
			
		}else if(commandParts.length != 1){
			throw new UnsupportedNumberofArgumentsException();
		}
		printDebugOutput(String.format("Decoded command to: %s", command.toString()));
		return command;
	}

    
    private void executeCommand(Command command) throws UnknownCommandException {
    	switch(command.getOpCode()) {
    	case PSZ:
    		psz(command.getFirstParam());
    		break;
    	case ADD:
    		add();
    		break;
    	case PRT:
    		prt();
    		break;
		case AAL:
			aal();
			break;
		case AND:
			and();
			break;
		case DIV:
			div();
			break;
		case END:
			end();
			break;
		case GTR:
			gtr();
			break;
		case JMP:
			jmp();
			break;
		case JMT:
			jmt();
			break;
		case LES:
			les();
			break;
		case MOD:
			mod();
			break;
		case MUL:
			mul();
			break;
		case OHR:
			ohr();
			break;
		case PSA:
			psa(command.getFirstParam());
			break;
		case PSI:
			psi(command.getFirstParam());
			break;
		case SUB:
			sub();
			break;
		case PSW:
			psw(command.getFirstParam());
			break;
		case EQL:
			eql();
			break;
		case NOT:
			not();
			break;
		case SAV:
			sav();
			break;
		default:
			throw new UnknownCommandException(command.getOpCode().toString());
		}
    	printDebugOutput(String.format("Executed command: %s",command.getOpCode().toString()));
    	printDebugOutput(String.format("Stack: %s", stack.toString()));
    	printDebugOutput(String.format("Registers: %s", printRegisters()));
	}

	private String printRegisters() {
		boolean empty = true;
		String registers = "[";
		for(int i = 0; i< PROGRAM_MEMORY_SIZE; i++) {
			StackElement element = programmMemory[i];
			if(element != null) {
				empty = false;
				String register = String.format("%d -> %s, ", i, element.toString());
				registers = registers + register;
			}
		}
		if(!empty) {
			//remove last " ," from string
			registers = StringUtils.removeLastCharacters(registers, 2);
		}
		registers = registers + "]";
		return registers;
	}

	



	private void sav() {
		StackElement element = stack.pop();	
		int address = popAddress();
		programmMemory[address] = element;
	}

	private void not() {
		boolean isso = popIsso();
		isso = !isso;
		pushIsso(isso);	
	}

	private void eql() {
		StackElement arg1 = stack.pop();
		StackElement arg2 = stack.pop();
		
		if(arg1.equals(arg2)) {
			pushIsso(true);
		}else {
			pushIsso(false);
		}
	}

	private void psw(String word) {
		pushWord(word);
	}

	private void sub() {
		double a = popZal();
		double b = popZal();
		double result = b - a;
		pushZal(result);
	}

	private void mod() {
		double a = popZal();
		double b = popZal();
		double result = b % a;
		pushZal(result);

	}

	private void les() {
		double a = popZal();
		double b = popZal();
		boolean result = b < a;
		pushIsso(result);

	}

	private void jmt() {
		int address = popAddress();
		boolean isso = popIsso();
		if (isso) {
			instructionPointer = address;
		}
	}

	private void jmp() {
		int address = popAddress();
		instructionPointer = address;
	}

	private void ohr() {
		boolean a = popIsso();
		boolean b = popIsso();
		boolean result = b || a;
		pushIsso(result);

	}

	private void psa(String firstParam) {
		pushAddress(Integer.parseInt(firstParam));
	}

	private void psi(String firstParam) {
		pushIsso(parseIsso(firstParam));

	}

	private boolean parseIsso(String firstParam) {
		int isso = Integer.parseInt(firstParam);
		return isso != 0 ? true : false;
	}

	private void gtr() {
		double a = popZal();
		double b = popZal();
		boolean result = b > a;
		pushIsso(result);

	}

	private void div() {
		double a = popZal();
		double b = popZal();
		double result = b / a;
		pushZal(result);
	}

	private void and() {
		boolean a = popIsso();
		boolean b = popIsso();
		boolean result = b && a;
		pushIsso(result);
	}

	private void aal() {
		printOutput("Halo I bims 1 aal vong Halo Wörlt her");
	}

	private void end() {
		running = false;
		printDebugOutput("Programm exited without error");
	}

	private void mul() {
		double a = popZal();
		double b = popZal();
		pushZal(b * a);
	}

	private void psz(String arg) {
		pushZal(Double.parseDouble(arg));
	}

	private void prt() {
		StackElement element = stack.pop();
		String out = element.toString();
		if (element.getType() == Type.ADDRESS) {
			int address = (int) element.getValue();
			StackElement global = programmMemory[address];
			out = out + " -> " + global.toString();
		}
		printOutput(out);
	}

	private void add() {
		double a = popZal();
		double b = popZal();
		double result = b + a;
		pushZal(result);
	}

	private void pushZal(double zal) {
		StackElement element = new StackElement(Type.ZAL, zal);
		stack.push(element);
	}

	private void pushAddress(int address) {
		StackElement element = new StackElement(Type.ADDRESS, address);
		stack.push(element);
	}

	private void pushIsso(boolean isso) {
		StackElement element = new StackElement(Type.ISSO, isso);
		stack.push(element);
	}

	private void pushWord(String word) {
		StackElement element = new StackElement(Type.WORD, word);
		stack.push(element);
	}

	private double popZal() {
		StackElement element = stack.pop();
		double zal;
		if (element.getType() == Type.ADDRESS) {
			zal = loadZal((int) element.getValue());
		} else {
			zal = (double) element.getValue();
		}
		return zal;
	}

	private int popAddress() {
		StackElement element = stack.pop();
		int address = (int) element.getValue();
		return address;
	}

	private boolean popIsso() {
		StackElement element = stack.pop();
		boolean isso;
		if (element.getType() == Type.ADDRESS) {
			isso = loadIsso((int) element.getValue());
		} else {
			isso = (boolean) element.getValue();
		}
		return isso;
	}

	private String popWord() {
		StackElement element = stack.pop();
		String word;
		if (element.getType() == Type.ADDRESS) {
			word = loadWord((int) element.getValue());
		} else {
			word = (String) element.getValue();
		}
		return word;
	}

	private double loadZal(int address) {
		double zal = (double) programmMemory[address].getValue();
		return zal;
	}

	private boolean loadIsso(int address) {
		boolean isso = (boolean) programmMemory[address].getValue();
		return isso;
	}

	private String loadWord(int address) {
		String word = (String) programmMemory[address].getValue();
		return word;
	}
	
	private void printErrorOutput(String error) {
		errorOut.println(error);
	}
	
	private void printOutput(String message) {
		standardOut.print(message);
	}
	
	private void printDebugOutput(String message) {
		if(debugOutput) {
			standardOut.println(message);	
		}
	}
}
