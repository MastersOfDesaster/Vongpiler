package vong.piler.her.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import vong.piler.her.steakmachine.OperationEnum;

public class ByteCodeWriter {
	
	private static Logger logger = LogManager.getLogger(ByteCodeWriter.class);
	
	private List<String> linesToWrite;
	
	private File file;
	
	private RegisterHandler registerHandler;
	
	public ByteCodeWriter(File file) {
		this.file = file;
		linesToWrite = new ArrayList<>();
		registerHandler = RegisterHandler.getInstance();
	}

	public ByteCodeWriter(String string) {
		this(new File(string));
	}

	void addCommand(OperationEnum command) {
		linesToWrite.add(registerHandler.addOperation(command));
	}

	void addCommand(OperationEnum command, String para) {
		linesToWrite.add(registerHandler.addOperation(command, para));
	}

	void addCommand(OperationEnum command, int address, int count) {
		linesToWrite.add(registerHandler.addOperation(command, address, count));
	}

	void addCommand(OperationEnum command, String address, int count) {
		linesToWrite.add(registerHandler.addOperation(command, address, count));
	}
	
	void addMultiCommand(List<String> commands){
		linesToWrite.addAll(commands);
	}
	
	void eof() {
		linesToWrite.add(registerHandler.addOperation(OperationEnum.END));
		writeToFile();
	}
	
	void addPrt() {
		linesToWrite.add(registerHandler.addOperation(OperationEnum.PRT));
	}
	
	void addAAL() {
		linesToWrite.add(registerHandler.addOperation(OperationEnum.AAL));
	}
	
	void addNOT() {
		linesToWrite.add(registerHandler.addOperation(OperationEnum.NOT));
	}
	
	void fillBlankAddress(String blank, String address) { //TODO: Maybe add start index to shorten the loop
		blank = ":X" + blank + "X:";
		int index = 0;
		for (String line : linesToWrite) {
			if (line.contains(blank)) {
				linesToWrite.set(index, line.replace(blank, address));
				break;
			}
			index++;
		}
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
