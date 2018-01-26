package vong.piler.her.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import vong.piler.her.enums.OperationEnum;

import org.apache.logging.log4j.LogManager;

public class ByteCodeWriter {
	
	private static Logger logger = LogManager.getLogger(ByteCodeWriter.class);
	
	private List<String> linesToWrite;
	private File file;
	private RegisterHandler registerHandler;
	
	public ByteCodeWriter(File file) {
		this.file = file;
		linesToWrite = new ArrayList<>();
		this.registerHandler = RegisterHandler.getInstance();
	}

	public ByteCodeWriter(String string) {
		this(new File(string));
	}
	
	private void addLine(String line) {		
		linesToWrite.add(line);
		registerHandler.operationAdded();
	}

	void addCommand(OperationEnum command) {
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(command.ordinal());
		addLine(operationBuilder.toString());
		logger.debug("added operation " +  operationBuilder.toString());
	}

	void addCommand(OperationEnum command, String parameter) {
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(command.ordinal());
		operationBuilder.append(" ");
		operationBuilder.append(parameter);
		addLine(operationBuilder.toString());
		logger.debug("added operation " +  operationBuilder.toString());
	}
	
	void addCommandResolveAdresses(OperationEnum command, String parameter) {
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(command.ordinal());
		operationBuilder.append(" ");
		if (command.equals(OperationEnum.PSA))
			operationBuilder.append(registerHandler.getVariableAddress(parameter));
		else
			operationBuilder.append(parameter);
		addLine(operationBuilder.toString());
		logger.debug("added operation " +  operationBuilder.toString());
	}

	void addCommand(OperationEnum command, int address, int count) {
		StringBuilder operationBuilder = new StringBuilder();
		operationBuilder.append(command.ordinal());
		operationBuilder.append(" ");
		operationBuilder.append(address);
		operationBuilder.append(" ");
		operationBuilder.append(count);
		addLine(operationBuilder.toString());
		logger.debug("added operation " +  operationBuilder.toString());
	}
	
	void eof() {
		addCommand(OperationEnum.END);
		writeToFile();
	}
	
	void addPrt() {
		addCommand(OperationEnum.PRT);
	}
	
	void addAAL() {
		addCommand(OperationEnum.AAL);
	}
	
	void addNOT() {
		addCommand(OperationEnum.NOT);
	}
	
	void addBlank(String blankMark) {
		String blank = ":X" + blankMark + "X:";
		addCommand(OperationEnum.PSA, blank);
	}
	
	void fillBlankAddress(String blank, String address, int startIndex) {
		blank = ":X" + blank + "X:";
		for (int index=startIndex; index<linesToWrite.size(); index++) {
			String line = linesToWrite.get(index);
			if (line.contains(blank)) {
				linesToWrite.set(index, line.replace(blank, address));
				break;
			}
		}
	}
	
	int getLineNumber() {
		return linesToWrite.size();
	}
	
	private boolean writeToFile() {
		try {
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
		logger.info("vong class her was successfully written");
		return true;
	}
}
