package vong.piler.her.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vong.piler.her.steakmachine.OperationEnum;

public class ByteCodeWriter {
	
	private List<String> linesToWrite;
	
	private String filename;
	
	public ByteCodeWriter(String filename) {
		linesToWrite = new ArrayList<>();
		this.filename = filename.replace(".vsh", "");
	}

	void addCommand(OperationEnum command) {
		StringBuilder commandBuilder = new StringBuilder(command.ordinal());
		linesToWrite.add(commandBuilder.toString());
	}

	void addCommand(OperationEnum command, String para) {
		StringBuilder commandBuilder = new StringBuilder(command.ordinal());
		commandBuilder.append(" ");
		commandBuilder.append(para);
		linesToWrite.add(commandBuilder.toString());
	}

	void addCommand(OperationEnum command, String address, int count) {
		StringBuilder commandBuilder = new StringBuilder(command.ordinal());
		commandBuilder.append(" ");
		commandBuilder.append(address);
		commandBuilder.append(" ");
		commandBuilder.append(count);
		linesToWrite.add(commandBuilder.toString());
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
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
