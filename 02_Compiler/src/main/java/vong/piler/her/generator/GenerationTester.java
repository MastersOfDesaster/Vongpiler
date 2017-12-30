package vong.piler.her.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import vong.piler.her.steakmachine.OperationEnum;

public class GenerationTester {
	
	public static void main(String[] args) {
		calculationTester();
//		comparatorZalTester();
	}

	private static void calculationTester() {
		List<ValueModel> values = new ArrayList<>(4);
		values.add(new ValueModel(1000.0));		
		values.add(new ValueModel(100.0));
		values.add(new ValueModel(10.0));
		values.add(new ValueModel(1.0));
		Executor executor = Executors.newCachedThreadPool();
		executor.execute(() -> addTester(values));
		/*executor.execute(() -> subTester(values));
		executor.execute(() -> mulTester(values));
		executor.execute(() -> divTester(values));
		executor.execute(() -> modTester(values));*/
	}
	
	private static void addTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/add.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.ADD, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void subTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/sub.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.SUB, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void mulTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/mul.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.MUL, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void divTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/div.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.DIV, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void modTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/mod.vsh");
		writer.addMultiCommand(GeneratorMethods.generateCalculations(OperationEnum.MOD, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void comparatorZalTester() {
		List<ValueModel> values = new ArrayList<>(4);
		values.add(new ValueModel(1000.0));		
		values.add(new ValueModel(100.0));
		values.add(new ValueModel(10.0));
		values.add(new ValueModel(1.0));
		Executor executor = Executors.newCachedThreadPool();
		executor.execute(() -> lesTester(values));
		executor.execute(() -> gtrTester(values));
		executor.execute(() -> eqiTester(values));
		executor.execute(() -> nqiTester(values));
	}

	private static void lesTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/les.vsh");
		Collections.reverse(innerValues);
		writer.addMultiCommand(GeneratorMethods.generateComparator(OperationEnum.LES, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void gtrTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/gtr.vsh");
		writer.addMultiCommand(GeneratorMethods.generateComparator(OperationEnum.GTR, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void eqiTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/eqi.vsh");
		writer.addMultiCommand(GeneratorMethods.generateComparator(OperationEnum.EQI, innerValues));
		writer.addPrt();
		writer.eof();
	}
	
	private static void nqiTester(List<ValueModel> values) {
		List<ValueModel> innerValues = values;
		ByteCodeWriter writer = new ByteCodeWriter("gen/generatorTester/nqi.vsh");
		writer.addMultiCommand(GeneratorMethods.generateComparator(OperationEnum.NQI, innerValues));
		writer.addPrt();
		writer.eof();
	}
}
