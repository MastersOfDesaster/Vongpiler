package vong.piler.her;

import java.net.URI;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import vong.piler.her.generator.Generator;
import vong.piler.her.lexer.Lexer;
import vong.piler.her.lexer.Token;
import vong.piler.her.parser.Parser;
import vong.piler.her.parser.TreeNode;

public class Main {
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
			
			if(line.hasOption("d")) {
				//TODO implement in lexer
				//TODO implement in parser
				//TODO implement in generator
			}
			if(line.hasOption("o")) {
				//TODO implement in generator
			}
			if(line.hasOption("h")) {
				printHelp(createOptions());
			}else {
				try {
					String filename = line.getArgs()[0];
					List<Token> tokenList = lexer.lex(filename);
					TreeNode root = parser.parse(tokenList);

					generator = new Generator(new URI(filename));
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
    	options.addOption(new Option("o", "Fad wo du dens fertigem Dateikompilat haben wilst!!"));
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
