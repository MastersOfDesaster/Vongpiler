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
import vong.piler.her.logger.LoggerVongManagerHer;
import vong.piler.her.steakmachine.OperationEnum;

public class ByteCodeWriter {
	
	private static Logger logger = LoggerVongManagerHer.getLogger(ByteCodeWriter.class);
	
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

	void addCommand(OperationEnum command, String para) throws WrongNumberOfArgumentsException {
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
	
	void eof() {
		try {
			linesToWrite.add(registerHandler.addOperation(OperationEnum.END));
			writeToFile();
		} catch (WrongNumberOfArgumentsException e) {
			logger.error("could not write the END operation", e);
			return;
		}
	}
	
	void addPrt() {
		try {
			linesToWrite.add(registerHandler.addOperation(OperationEnum.PRT));
		} catch (WrongNumberOfArgumentsException e) {
			logger.error("could not write the PRT operation", e);
			return;
		}
	}
	
	void addAAL() {
		try {
			linesToWrite.add(registerHandler.addOperation(OperationEnum.AAL));
		} catch (WrongNumberOfArgumentsException e) {
			logger.error("could not write the AAL operation", e);
			return;
		}
	}
	
	private boolean writeToFile() {
		try {
			File file = new File(filename + ".vch");
			logger.debug("ByteCode will be written to file " + file.getAbsolutePath());
			if (!file.exists()) {
				file.createNewFile();
				logger.debug("File was created");
			}
			FileWriter fw = new FileWriter(file);
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
		logger.debug("vong class her was successfully written");
		return true;
	}
}
