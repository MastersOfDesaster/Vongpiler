package vong.piler.her;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import vong.piler.her.lexer.Lexer;
import vong.piler.her.lexer.Token;
import vong.piler.her.parser.Parser;

public class Main {
    public static void main(String[] args) {
        try {
            // read source-file
            URI sourceUri = Main.class.getClassLoader().getResource("ibims1code.vsh").toURI();
            String source = new String(Files.readAllBytes(Paths.get(sourceUri)));
            
            // generate token-list
            Lexer lexer = new Lexer();
            List<Token> tokenList = lexer.lex(source);
            
            // parse token-list
            Parser parser = new Parser();
            parser.parse(tokenList);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Generator generator = new Generator();
//        Parser parser = new Parser(lexer.lex());
//        Steakmachine steakmachine = new Steakmachine(parser.parse());
    }
}
