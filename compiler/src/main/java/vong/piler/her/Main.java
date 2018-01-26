package vong.piler.her;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import vong.piler.her.generator.Generator;
import vong.piler.her.lexer.Lexer;
import vong.piler.her.lexer.Token;
import vong.piler.her.parser.Parser;
import vong.piler.her.parser.TreeNode;

public class Main {
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
	
    public static void main(String[] args) {
    	Lexer lexer = new Lexer();
    	Parser parser = new Parser();
    	Generator generator;
    	
    	Options options = createOptions();
    	
		if(args.length == 0) {
			printHelp(options);
			return;
		}
		
		CommandLineParser cliParser = new DefaultParser();
		try {
			CommandLine cmdLine = cliParser.parse(createOptions(), args);
			String output = null;
			
			if(cmdLine.hasOption("d")) {
				Configurator.setLevel("vong.piler.her", Level.DEBUG);
			}
			if(cmdLine.hasOption("o")) {
				output = cmdLine.getOptionValue("o");
			}
			if(cmdLine.hasOption("h")) {
				printHelp(createOptions());
			}else {
				try {
					String filename = cmdLine.getArgs()[0];
					
					InputStream is = new ByteArrayInputStream(Files.readAllBytes(new File(filename).toPath()));
					BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					StringBuilder sourceBuilder = new StringBuilder();
					Iterator<String> sourceIterator = br.lines().iterator();
					while(sourceIterator.hasNext()) {
						sourceBuilder.append(sourceIterator.next()).append("\n");
					}
					String source = sourceBuilder.toString();
					
					List<Token> tokenList = lexer.lex(source);
					TreeNode root = parser.parse(tokenList);

					if(output == null) {
						output = filename.replace(".vsh", ".vch");	
					}
					if(cmdLine.hasOption("d")) {
						testPrint(root, 2);
					}
					generator = new Generator(output);
					generator.start(root);
					
				}catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
					System.err.println("I kan di Datei nit findn");
				} catch (Exception e) {
					System.err.println("Halo I bims 1 Ausname:");
					e.printStackTrace();
				}
			}
						
		} catch (ParseException e) {
			System.err.println("Du hamst 1 Feler gem8 du Lauch");
			printHelp(createOptions());
		}
    	
          }
    
    private static Options createOptions() {
    	Options options = new Options();
    	options.addOption(new Option("h", "Gibd dir dise nachricht du Larry!!"));
    	options.addOption(new Option("d", "Vongpiler talkt vong debug her!!"));
    	options.addOption(new Option("o", true, "Fad wo du dens fertigem Dateikompilat haben wilst!!"));
    	return options;
    }
    
	private static void printHelp(Options options) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("vongc", "\n", options, "\n", true);
	}
    
    private static void testPrint(TreeNode root, int tabCount) {
		System.out.println("\t\t" + root.getName());
		leftPrint(root.getLeft(), tabCount-1);
		if (root.getRight() != null)
			testPrint(root.getRight(), ++tabCount);
	}
	
	private static void leftPrint(Object left, int tabCount) {
		for (int i = 0; i < tabCount; i++) {
			System.out.print("\t");
		}
		if (left == null)
			System.out.print("null");
		else if (left instanceof Double) 
			System.out.print(((Double) left));
		else if (left instanceof Boolean) 
			System.out.print(((Boolean) left));
		else if (left instanceof String) 
			System.out.print(((String) left));
		else 
			System.out.print(left.toString());
	}
}
