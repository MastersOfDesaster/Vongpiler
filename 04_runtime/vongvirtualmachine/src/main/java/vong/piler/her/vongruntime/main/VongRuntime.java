package vong.piler.her.vongruntime.main;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import vong.piler.her.vongruntime.virtualmachine.Steakmachine;

public class VongRuntime {
	
	public static void main(String[] args) {
		Options options = createOptions();
		
		Steakmachine vvm = new Steakmachine();
		vvm.init();
		
		if(args.length == 0) {
			printHelp(options);
			return;
		}
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(createOptions(), args);
			
			if(line.hasOption("a")) {
				vvm.setReadAssembler(true);
			}
			if(line.hasOption("d")) {
				vvm.setDebugOutput(true);
			}
			if(line.hasOption("h")) {
				printHelp(createOptions());
			}else {
				try {
					String filename = line.getArgs()[0];
					vvm.load(new File(filename));
					vvm.run();		
				}catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
					System.err.println("I kan di Datei nit findn");
				}

			}
						
		} catch (ParseException e) {
			System.err.println("Du hamst 1 Feler gem8 du Lauch");
			printHelp(createOptions());
		}
	
	}
	
	private static void printHelp(Options options) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("vong", "\n", options, "\n", true);
	}
	
	private static Options createOptions() {
		Options options = new Options();
		
		options.addOption(new Option("h", "Gibd dir dise nachricht du Larry!!"));
		options.addOption(new Option("d", "Vongruntime talkt vong debug her!!"));
		options.addOption(new Option("a", "I nem jez asembla kot!!"));
		options.addOption(new Option("s", "I bims 1 sinloses option vong swag her"));
		return options;
	}
	
}
