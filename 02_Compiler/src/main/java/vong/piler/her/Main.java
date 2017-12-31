package vong.piler.her;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.runners.parameterized.TestWithParameters;

import vong.piler.her.generator.Generator;
import vong.piler.her.lexer.Lexer;
import vong.piler.her.lexer.Token;
import vong.piler.her.parser.Parser;
import vong.piler.her.parser.TreeNode;

public class Main {
    public static void main(String[] args) {
        try {
            // read source-file
            URI sourceUri = Main.class.getClassLoader().getResource("fibonacci.vsh").toURI();
            String source = new String(Files.readAllBytes(Paths.get(sourceUri)));
            
            // generate token-list
            Lexer lexer = new Lexer();
            List<Token> tokenList = lexer.lex(source);
            
            // parse token-list
            Parser parser = new Parser();
            TreeNode root = parser.parse(tokenList);
            
            testPrint(root, 2);
            //Code generation
            Generator generator = new Generator(sourceUri);
            generator.generate(root);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
