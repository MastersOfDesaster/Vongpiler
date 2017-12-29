package vong.piler.her;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import vong.piler.her.lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        try {
            URI sourceUri = Main.class.getClassLoader().getResource("ibims1code.vsh").toURI();
            String source = new String(Files.readAllBytes(Paths.get(sourceUri)));
            Lexer lexer = new Lexer();
            lexer.lex(source);
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
