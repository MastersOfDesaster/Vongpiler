package vong.piler.her.generator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import vong.piler.her.enums.OperationEnum;

public class ComparatorZalGenarationTest {
	
	private List<ValueModel> values = new ArrayList<>(4);
	private static String filePrefix = "gen/generatorTester/";
	private static String correctFiles = "src/main/test/vong/piler/her/generator/correct/files/";

	@Before
	public void setUp() throws Exception {
		values.add(new ValueModel(1000.0));		
		values.add(new ValueModel(100.0));
		values.add(new ValueModel(10.0));
		values.add(new ValueModel(1.0));
	}

	@Test
	public void lesTest() {
		ByteCodeWriter writer = new ByteCodeWriter(filePrefix + "les.vsh");
		Collections.reverse(values);
		PreDefinedFunction.generateComparator(OperationEnum.LES, values, writer);
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("les.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void gtrTest() {
		ByteCodeWriter writer = new ByteCodeWriter(filePrefix + "gtr.vsh");
		PreDefinedFunction.generateComparator(OperationEnum.GTR, values, writer);
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("gtr.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void eqzTest() {
		ByteCodeWriter writer = new ByteCodeWriter(filePrefix + "eqz.vsh");
		PreDefinedFunction.generateComparator(OperationEnum.EQL, values, writer);
		writer.addPrt();
		writer.eof();
		try {
			assertTrue("Files are not equal", testFileContent("eqz.vch"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	private boolean testFileContent(String filename) throws IOException {
		boolean same = true;
		File file1 = new File(filePrefix + filename);
		File file2 = new File(correctFiles + filename);
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
	}

}
