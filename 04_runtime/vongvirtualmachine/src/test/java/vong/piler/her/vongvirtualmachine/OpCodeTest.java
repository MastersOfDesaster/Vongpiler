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
	public void testAddWithPositives(){
		loadFile("addWithPositives.vch");
		vvm.run();
		assertEquals("zal: 16.0", standardOut.toString());
	}
	
	@Test
	public void testAddWithNegatives(){
		loadFile("addWithNegatives.vch");
		vvm.run();
		assertEquals("zal: -16.0", standardOut.toString());
	}
	
	@Test
	public void testAddWithNegativeAndPositive(){
		loadFile("addWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("zal: -2.0", standardOut.toString());
	}
	
	@Test
	public void testMulWithPositives(){
		loadFile("mulWithPositives.vch");
		vvm.run();
		assertEquals("zal: 35.0", standardOut.toString());
	}
	
	@Test
	public void testMulWithNegatives(){
		loadFile("mulWithNegatives.vch");
		vvm.run();
		assertEquals("zal: 40.0", standardOut.toString());
	}
	
	@Test
	public void testMulWithNegativeAndPositive(){
		loadFile("mulWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("zal: -35.0", standardOut.toString());
	}
	
	@Test
	public void testDivWithPositives(){
		loadFile("divWithPositives.vch");
		vvm.run();
		assertEquals("zal: 2.0", standardOut.toString());
	}
	
	@Test
	public void testDivWithNegatives(){
		loadFile("divWithNegatives.vch");
		vvm.run();
		assertEquals("zal: 2.0", standardOut.toString());
	}
	
	@Test
	public void testDivWithNegativeAndPositive(){
		loadFile("divWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("zal: -2.0", standardOut.toString());
	}
	
	@Test
	public void testSubWithPositives(){
		loadFile("subWithPositives.vch");
		vvm.run();
		assertEquals("zal: 8.0", standardOut.toString());
	}
	
	@Test
	public void testSubWithNegatives(){
		loadFile("subWithNegatives.vch");
		vvm.run();
		assertEquals("zal: 2.0", standardOut.toString());
	}
	
	@Test
	public void testSubWithNegativeAndPositive(){
		loadFile("subWithNegativeAndPositive.vch");
		vvm.run();
		assertEquals("zal: -16.0", standardOut.toString());
	}
	
	@Test
	public void testModWithRemainder(){
		loadFile("modWithRemainder.vch");
		vvm.run();
		assertEquals("zal: 1.0", standardOut.toString());
	}
	
	
	@Test
	public void testModWithoutRemainder(){
		loadFile("modWithoutRemainder.vch");
		vvm.run();
		assertEquals("zal: 0.0", standardOut.toString());
	}
	
	@Test
	public void testLesWhenTrue(){
		loadFile("lesWhenTrue.vch");
		vvm.run();
		assertEquals("isso: yup", standardOut.toString());
	}
	
	@Test
	public void testLesWhenFalse(){
		loadFile("lesWhenFalse.vch");
		vvm.run();
		assertEquals("isso: nope", standardOut.toString());
	}
	
	@Test
	public void testGtrWhenTrue(){
		loadFile("gtrWhenTrue.vch");
		vvm.run();
		assertEquals("isso: yup", standardOut.toString());
	}
	
	@Test
	public void testGtrWhenFalse(){
		loadFile("gtrWhenFalse.vch");
		vvm.run();
		assertEquals("isso: nope", standardOut.toString());
	}
	
	@Test
	public void testJmpToLegalLine(){
		loadFile("jmpToLegalLine.vch");
		vvm.run();
		assertEquals("Halo I bims 1 aal vong Halo Wörlt her", standardOut.toString());
	}
	
	@Test
	public void testJmpToIllegalLine(){
		loadFile("jmpToIllegalLine.vch");
		vvm.run();
		assertEquals("instruction-pointer at empty register\r\n", errorOut.toString());
	}
	
	private void loadFile(String name) {
		File file = new File(getClass().getClassLoader().getResource(name).getFile());
		vvm.load(file);
	}

}
