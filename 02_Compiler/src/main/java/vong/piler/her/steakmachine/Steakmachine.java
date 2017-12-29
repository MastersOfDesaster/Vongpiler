package vong.piler.her.steakmachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;

public class Steakmachine {
	
	private static final int PROGRAM_MEMORY_SIZE = 100;
	private static final int CODE_MEMORY_SIZE = 100;
	private Stack<Object> stack;
	private Object[] programmMemory;
	private String[] codeMemory;
	
	private int instructionPointer = 0;
	private boolean running;
	
	
	
	public static void main(String[] args) {
		Steakmachine steack = new Steakmachine();
		steack.init();
		URL url = steack.getClass().getResource("zalenAddierenDiesDas.vch");
		steack.load(new File(url.getPath()));
		steack.run();
	}
	
	private void load(File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		String line;
		int i = 0;
		while((line = br.readLine()) != null) {
			codeMemory[i] = line;
			i++;			
		}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void init() {
		stack = new Stack<>();
		codeMemory = new String[CODE_MEMORY_SIZE];
		programmMemory = new Object[PROGRAM_MEMORY_SIZE];
		instructionPointer = 0;
	}

	public void run() {
		running = true;
		while(running) {
    		String rawCommand = readCommand();
    		Command command = decodeCommand(rawCommand);
    		executeCommand(command);
    		instructionPointer++;
    	}
        
    }
	
    private String readCommand() {
    	String rawCommand = codeMemory[instructionPointer];
    	return rawCommand;
    }
    
    private Command decodeCommand(String rawCommand) {
    	Command command = new Command();
    	String[] commandParts = rawCommand.split(" ");
    	switch(commandParts.length) {
    	case 1:
    		command.setOpCode(OperationEnum.valueOf(commandParts[0]));
    		break;
    	case 2: 
    		command.setOpCode(OperationEnum.valueOf(commandParts[0]));
    		command.setFirstParam(commandParts[1]);
    		break;
    		default:
    			//TODO throw exception
    	}
    	return command;
    }
    
    private void executeCommand(Command command) {
    	switch(command.getOpCode()) {
    	case PSZ:
    		stack.push(Double.parseDouble(command.getFirstParam().toString()));
    		 		   		break;
    	case ADD:
    		double a = (double) stack.pop();
    		double b = (double) stack.pop();
    		double result = a + b;
    		stack.push(result);
    		break;
    	case PRT:
    		String out = stack.pop().toString();
    		System.out.println(out);
		case AAL:
			break;
		case AND:
			break;
		case DIV:
			break;
		case END:
			running = false;
			break;
		case EQL:
			break;
		case GTR:
			break;
		case JMP:
			break;
		case JMT:
			break;
		case LES:
			break;
		case MOD:
			break;
		case MUL:
			break;
		case NQL:
			break;
		case OHR:
			break;
		case PSA:
			break;
		case PSI:
			break;
		case SAV:
			break;
		case SUB:
			break;
		default:
			break;
    	}
    }

	private void stop() {
		// TODO Auto-generated method stub
		
	}
    
}
