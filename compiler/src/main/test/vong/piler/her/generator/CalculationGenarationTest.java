package vong.piler.her.generator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import vong.piler.her.enums.OperationEnum;

public class CalculationGenarationTest {
	
	private List<ValueModel> values = new ArrayList<>(4);

	@Before
	public void setUp() throws Exception {
		values.add(new ValueModel(1000.0));		
		values.add(new ValueModel(100.0));
		values.add(new ValueModel(10.0));
		values.add(new ValueModel(1.0));
	}
/*
	@Test
	public void additionTest() {
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/add.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.ADD, values));
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("add.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void substraktionTest() {
		ByteCodeWriter writer = new ByteCodeWriter("generatorTester/sub.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.SUB, values));
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("sub.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void multiplicationTest() {
		ByteCodeWriter writer = new ByteCodeWriter("generatorTester/mul.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.MUL, values));
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("mul.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void divisionTest() {
		ByteCodeWriter writer = new ByteCodeWriter("generatorTester/div.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.DIV, values));
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("div.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void moduloTest() {
		ByteCodeWriter writer = new ByteCodeWriter("generatorTester/mod.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.MOD, values));
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("mod.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	private boolean testFileContent(String filename) throws IOException {
		boolean same = true;
		File file1 = new File("gen/generatorTester/" + filename);
		File file2 = new File("src/main/test/vong/piler/her/generator/correct/files/" + filename);
		FileReader fr1 = new FileReader(file1);
		FileReader fr2 = new FileReader(file2);
		BufferedReader br1 = new BufferedReader(fr1);
		BufferedReader br2 = new BufferedReader(fr2);
		String br1Line = "";
		while ((br1Line = br1.readLine()) != null) {
			if (!br1Line.equals(br2.readLine())) {
				same = false;
			}
		}
		br1.close();
		br2.close();
		fr1.close();
		fr2.close();
		return same;
	}*/

}
