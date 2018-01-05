package vong.piler.her.vongvirtualmachine;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import vong.piler.her.vongruntime.virtualmachine.Steakmachine;

public class OpCodeTest {
	
	Steakmachine vvm = new Steakmachine();
	ByteArrayOutputStream standardOut = new ByteArrayOutputStream();
	ByteArrayOutputStream errorOut = new ByteArrayOutputStream();
	
	
	@Before
	public void init() {
		vvm.setStandardOut(new PrintStream(standardOut));
		vvm.setErrorOut(new PrintStream(errorOut));
		vvm.setReadAssembler(true);
		vvm.init();
	}
	
	@Test
	public void testWordOutputWithSpaces() {
		loadFile("wordOutputWithSpaces.vch");
		vvm.run();
		assertEquals("s w a g", getCleanVvmOutput());
	}
	
	@Test
	public void testWordOutput() {
		loadFile("wordOutput.vch");
		vvm.run();
		assertEquals("swag", getCleanVvmOutput());
	}

	@Test
	public void testAddWithPositives(){
		loadFile("addWithPositives.vch");
		vvm.run();
		assertEquals("16.0", getCleanVvmOutput());
	}
	
	@Test
	public void testAddWithNegatives(){
		loadFile("addWithNegatives.vch");
		vvm.run();
		assertEquals("-16.0", getCleanVvmOutput());
	}
	
	@Test
	public void testAddWithNegativeAndPositive(){
		loadFile("addWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("-2.0", getCleanVvmOutput());
	}
	
	@Test
	public void testMulWithPositives(){
		loadFile("mulWithPositives.vch");
		vvm.run();
		assertEquals("35.0", getCleanVvmOutput());
	}
	
	@Test
	public void testMulWithNegatives(){
		loadFile("mulWithNegatives.vch");
		vvm.run();
		assertEquals("40.0", getCleanVvmOutput());
	}
	
	@Test
	public void testMulWithNegativeAndPositive(){
		loadFile("mulWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("-35.0", getCleanVvmOutput());
	}
	
	@Test
	public void testDivWithPositives(){
		loadFile("divWithPositives.vch");
		vvm.run();
		assertEquals("2.0", getCleanVvmOutput());
	}
	
	@Test
	public void testDivWithNegatives(){
		loadFile("divWithNegatives.vch");
		vvm.run();
		assertEquals("2.0", getCleanVvmOutput());
	}
	
	@Test
	public void testDivWithNegativeAndPositive(){
		loadFile("divWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("-2.0", getCleanVvmOutput());
	}
	
	@Test
	public void testSubWithPositives(){
		loadFile("subWithPositives.vch");
		vvm.run();
		assertEquals("8.0", getCleanVvmOutput());
	}
	
	@Test
	public void testSubWithNegatives(){
		loadFile("subWithNegatives.vch");
		vvm.run();
		assertEquals("2.0", getCleanVvmOutput());
	}
	
	@Test
	public void testSubWithNegativeAndPositive(){
		loadFile("subWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("-16.0", getCleanVvmOutput());
	}
	
	@Test
	public void testModWithRemainder(){
		loadFile("modWithRemainder.vch");
		vvm.run();
		assertEquals("1.0", getCleanVvmOutput());
	}
	
	
	@Test
	public void testModWithoutRemainder(){
		loadFile("modWithoutRemainder.vch");
		vvm.run();
		assertEquals("0.0", getCleanVvmOutput());
	}
	
	@Test
	public void testLesWhenTrue(){
		loadFile("lesWhenTrue.vch");
		vvm.run();
		assertEquals("yup", getCleanVvmOutput());
	}
	
	@Test
	public void testLesWhenFalse(){
		loadFile("lesWhenFalse.vch");
		vvm.run();
		assertEquals("nope", getCleanVvmOutput());
	}
	
	@Test
	public void testGtrWhenTrue(){
		loadFile("gtrWhenTrue.vch");
		vvm.run();
		assertEquals("yup", getCleanVvmOutput());
	}
	
	@Test
	public void testGtrWhenFalse(){
		loadFile("gtrWhenFalse.vch");
		vvm.run();
		assertEquals("nope", getCleanVvmOutput());
	}
	
	@Test
	public void testJmpToLegalLine(){
		loadFile("jmpToLegalLine.vch");
		vvm.run();
		assertEquals("Halo I bims 1 aal vong Halo Wörlt her", getCleanVvmOutput());
	}
	
	@Test
	public void testJmpToIllegalLine(){
		loadFile("jmpToIllegalLine.vch");
		vvm.run();
		assertEquals("instruction-pointer at empty register", getCleanVvmErrorOutput());
	}
	
	@Test
	public void testEqlWithWords(){
		loadFile("eqlWithWords.vch");
		vvm.run();
		assertEquals("yup", getCleanVvmOutput());
	}
	
	@Test
	public void testEqlWithZals(){
		loadFile("eqlWithZals.vch");
		vvm.run();
		assertEquals("yup", getCleanVvmOutput());
	}
	
	@Test
	public void testEqlWithIssos(){
		loadFile("eqlWithIssos.vch");
		vvm.run();
		assertEquals("yup", getCleanVvmOutput());
	}
	
	@Test
	public void testEqlWithDifferentTypes(){
		loadFile("eqlWithDifferentTypes.vch");
		vvm.run();
		assertEquals("nope", getCleanVvmOutput());
	}
	
	private void loadFile(String name) {
		File file = new File(getClass().getClassLoader().getResource(name).getFile());
		vvm.load(file);
	}
	
	private String getCleanVvmOutput() {
		String raw = standardOut.toString();
		return raw.replaceAll("\n", "").replaceAll("\r", "");
	}
	
	private String getCleanVvmErrorOutput() {
		String raw = errorOut.toString();
		return raw.replaceAll("\n", "").replaceAll("\r", "");
	}

}
