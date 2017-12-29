package vong.piler.her.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vong.piler.her.Constants;
import vong.piler.her.exceptions.WrongNumberOfArgumentsException;
import vong.piler.her.steakmachine.OperationEnum;

public class ByteCodeWriter {
	
	private static Logger logger = LogManager.getLogger(Constants.loggerName);
	
	private List<String> linesToWrite;
	
	private String filename;
	
	private RegisterHandler registerHandler;
	
	public ByteCodeWriter(String filename) {
		linesToWrite = new ArrayList<>();
		this.filename = filename.replace(".vsh", "");
		registerHandler = RegisterHandler.getInstance();
	}

	void addCommand(OperationEnum command) throws WrongNumberOfArgumentsException {
		linesToWrite.add(registerHandler.addOperation(command));
	}

	void addCommand(OperationEnum command, int para) throws WrongNumberOfArgumentsException {
		linesToWrite.add(registerHandler.addOperation(command, para));
	}

	void addCommand(OperationEnum command, int address, int count) throws WrongNumberOfArgumentsException {
		linesToWrite.add(registerHandler.addOperation(command, address, count));
	}

	void addCommand(OperationEnum command, String address, int count) throws WrongNumberOfArgumentsException {
		linesToWrite.add(registerHandler.addOperation(command, address, count));
	}
	
	void addMultiCommand(List<String> commands){
		linesToWrite.addAll(commands);
	}
	
	boolean writeToFile() {
		try {
			FileWriter fw = new FileWriter(new File("./" + filename + ".vch"));
			BufferedWriter bw = new BufferedWriter(fw);
			linesToWrite.forEach(line -> {
				try {
					bw.write(line);
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			});
			bw.close();
			fw.close();
		} catch (IOException | RuntimeException e) {
			logger.error("Failed to write vong class her", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
