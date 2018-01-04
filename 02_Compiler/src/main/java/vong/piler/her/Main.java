package vong.piler.her;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

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
			CommandLine line = cliParser.parse(createOptions(), args);
			String output = null;
			
			if(line.hasOption("d")) {
				Configurator.setLevel("vong.piler.her", Level.DEBUG);
			}
			if(line.hasOption("o")) {
				output = line.getOptionValue("o");
			}
			if(line.hasOption("h")) {
				printHelp(createOptions());
			}else {
				try {
					String filename = line.getArgs()[0];
					
					String source = new String(Files.readAllBytes(new File(filename).toPath()));
					List<Token> tokenList = lexer.lex(source);
					TreeNode root = parser.parse(tokenList);

					if(output == null) {
						output = filename.replace(".vsh", ".vch");	
					}
					if(line.hasOption("d")) {
						testPrint(root, 2);
					}
					generator = new Generator(output);
					generator.generate(root);
					
				}catch(IndexOutOfBoundsException e) {
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
