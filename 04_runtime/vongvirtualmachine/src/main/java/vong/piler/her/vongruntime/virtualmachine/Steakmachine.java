package vong.piler.her.vongruntime.virtualmachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;

import vong.piler.her.vongruntime.virtualmachine.model.StackElement.Type;
import vong.piler.her.vongruntime.exception.InstructionPointerOutOfBoundsException;
import vong.piler.her.vongruntime.exception.UnknownCommandException;
import vong.piler.her.vongruntime.exception.UnsupportedNumberofArgumentsException;
import vong.piler.her.vongruntime.virtualmachine.model.Command;
import vong.piler.her.vongruntime.virtualmachine.model.OperationEnum;
import vong.piler.her.vongruntime.virtualmachine.model.StackElement;

public class Steakmachine {

	private static final int PROGRAM_MEMORY_SIZE = 100;
	private static final int CODE_MEMORY_SIZE = 100;
	private Stack<StackElement> stack;
	private StackElement[] programmMemory;
	private String[] codeMemory;

	private int instructionPointer = 0;
	private boolean running;

	private void load(File file) {
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
			System.out.println("Could not read file at: " + file.getAbsolutePath());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Programmcode doesn't fit in programmregister which supports " + CODE_MEMORY_SIZE + " commands");
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
				System.out.println("Wrong number of arguments submitted for operation");
				running = false;
			} catch (InstructionPointerOutOfBoundsException e) {
				System.out.println("Instruction pointer ran out of bounds");
				running = false;
			} catch (UnknownCommandException e) {
				System.out.println("Encountered unknown command");
			} 
		}

	}

	private String readCommand() throws InstructionPointerOutOfBoundsException {
		try {
			String rawCommand = codeMemory[instructionPointer];
			return rawCommand;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InstructionPointerOutOfBoundsException();
		}

	}
	
	 private Command decodeCommandOrdinal(String rawCommand) throws UnsupportedNumberofArgumentsException {
    	Command command = new Command();
    	String[] commandParts = rawCommand.split(" ");
    	int cmd = Integer.parseInt(commandParts[0]);
    	switch(commandParts.length) {
    	case 1:
    		command.setOpCode(OperationEnum.values()[cmd]);
    		break;
    	case 2: 
    		command.setOpCode(OperationEnum.values()[cmd]);
    		command.setFirstParam(commandParts[1]);
    		break;
    		default:
    			throw new UnsupportedNumberofArgumentsException();
    	}
    	return command;
    }

	private Command decodeCommand(String rawCommand) throws UnsupportedNumberofArgumentsException {
		Command command = new Command();
		String[] commandParts = rawCommand.split(" ");
		switch (commandParts.length) {
		case 1:
			command.setOpCode(OperationEnum.valueOf(commandParts[0]));
			break;
		case 2:
			command.setOpCode(OperationEnum.valueOf(commandParts[0]));
			command.setFirstParam(commandParts[1]);
			break;
		default:
			throw new UnsupportedNumberofArgumentsException();
		}
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
		case EQZ:
			eqz();
			break;
		case EQI:
			eqi();
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
		case NQI:
			nqi();
			break;
		case NQZ:
			nqz();
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
		case SVA:
			sva();
			break;
		case SVZ:
			svz();
			break;
		case SVI:
			svi();
			break;
		case EQW:
			eqw();
			break;
		case NQW:
			nqw();
			break;
		case PSW:
			psw(command.getFirstParam());
			break;
		case SVW:
			svw();
			break;
		default:
			throw new UnknownCommandException();
		}
	}

	private void svw() {
		String word = popWord();
		int address = popAddress();
		StackElement element = new StackElement(Type.WORD, word);
		programmMemory[address] = element;
	}

	private void psw(String word) {
		pushWord(word);
	}

	private void nqw() {
		String w1 = popWord();
		String w2 = popWord();
		boolean isso = !w1.equals(w2);
		pushIsso(isso);
	}

	private void eqw() {
		String w1 = popWord();
		String w2 = popWord();
		boolean isso = w1.equals(w2);
		pushIsso(isso);
	}

	private void eqz() {
		double a = popZal();
		double b = popZal();
		boolean isso = b == a;
		pushIsso(isso);
	}

	private void eqi() {
		boolean a = popIsso();
		boolean b = popIsso();
		boolean isso = b == a;
		pushIsso(isso);
	}

	private void nqz() {
		double a = popZal();
		double b = popZal();
		boolean isso = b != a;
		pushIsso(isso);
	}

	private void nqi() {
		boolean a = popIsso();
		boolean b = popIsso();
		boolean isso = b != a;
		pushIsso(isso);
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

	private void sva() {
		int address = popAddress();
		int storeAddress = popAddress();
		StackElement element = new StackElement(Type.ADDRESS, address);
		programmMemory[storeAddress] = element;
	}

	private void svi() {
		boolean isso = popIsso();
		int address = popAddress();
		StackElement element = new StackElement(Type.ISSO, isso);
		programmMemory[address] = element;
	}

	private void svz() {
		double zal = popZal();
		int address = popAddress();
		StackElement element = new StackElement(Type.ZAL, zal);
		programmMemory[address] = element;
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
		System.out.println("Halo I bims 1 aal vong Halo W�rlt her");
	}

	private void end() {
		running = false;
		System.out.println("Programm exited without error");
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
		System.out.println(out);
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

}
